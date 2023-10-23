package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.SocioDTO;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.byt3social.analisedocumental.exceptions.DadoNotFoundException;
import com.byt3social.analisedocumental.exceptions.DocumentoNotFoundException;
import com.byt3social.analisedocumental.exceptions.FileTypeNotSupportedException;
import com.byt3social.analisedocumental.models.*;
import com.byt3social.analisedocumental.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.*;

@Service
public class ProcessoService {
    @Autowired
    private ProcessoRepository processoRepository;
    @Autowired
    private DadoRepository dadoRepository;
    @Autowired
    private DadoSolicitadoRepository dadoSolicitadoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoSolicitadoRepository documentoSolicitadoRepository;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmazonS3Service amazonS3Service;

    @Transactional
    public void abrirProcesso(OrganizacaoDTO organizacaoDTO) {
        Processo novoProcesso = new Processo(organizacaoDTO);

        List<Dado> dados = dadoRepository.findDadoByPadrao(true);
        List<Documento> documentos = documentoRepository.findDocumentoByPadrao(true);

        List<DocumentoSolicitado> documentosSolicitados = new ArrayList<>();
        List<DadoSolicitado> dadosSolicitados = new ArrayList<>();

        for (Documento documento : documentos) {
            documentosSolicitados.add(new DocumentoSolicitado(documento, novoProcesso));
        }

        for (Dado dado : dados) {
            dadosSolicitados.add(new DadoSolicitado(dado, novoProcesso));
        }

        novoProcesso.vincularDadosSolicitados(dadosSolicitados);
        novoProcesso.vincularDocumentosSolicitados(documentosSolicitados);

        processoRepository.save(novoProcesso);
        dadoSolicitadoRepository.saveAll(dadosSolicitados);
        documentoSolicitadoRepository.saveAll(documentosSolicitados);

        notificarProspeccao(novoProcesso);
    }

    public List<Processo> consultarProcessos() {
        return processoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Processo consultarProcesso(Integer processoID) {
        return processoRepository.findById(processoID).get();
    }

    @Transactional
    public Processo salvarProcesso(Integer processoID, ProcessoDTO processoDTO, Boolean enviarParaAnalise) {
        Processo processo = processoRepository.findById(processoID).get();
        List<DadoSolicitado> dadosSolicitados = processo.getDadosSolicitados();
        List<Socio> socios = processo.getSocios();
        List<Socio> novosSocios = new ArrayList<>();

        for(DadoSolicitado dadoSolicitado : dadosSolicitados) {
            DadoSolicitadoDTO dadoSolicitadoDTO = processoDTO.dadosSolicitados().stream().filter(dadoSolicitadoDTO1 -> dadoSolicitadoDTO1.id().equals(dadoSolicitado.getId())).findFirst().get();

            dadoSolicitado.atualizar(dadoSolicitadoDTO);
        }

        ListIterator<Socio> socioListIterator = socios.listIterator();

        while(socioListIterator.hasNext()) {
            Socio socio = socioListIterator.next();

            Boolean contemSocio = processoDTO.socios().stream().anyMatch(socioDTO -> socioDTO.id() == socio.getId());

            if(!contemSocio) {
                socioListIterator.remove();
                socioRepository.delete(socio);
            }
        }

        for(SocioDTO socioDTO : processoDTO.socios()) {
            socios.stream().filter(socio -> socio.getId() == socioDTO.id()).findFirst().ifPresentOrElse(socio -> socio.atualizar(socioDTO, processo), () -> {
                Socio novoSocio = new Socio(socioDTO, processo);
                novosSocios.add(novoSocio);
            });
        }

        socioRepository.saveAll(novosSocios);

        processo.atualizar(processoDTO);

        if(enviarParaAnalise) {
            processo.atualizarStatus(StatusProcesso.EM_ANALISE);
        }

        return processo;
    }

    @Transactional
    public DocumentoSolicitado vincularDocumentoAoProcesso(Integer processoID, Integer documentoID) {
        Processo processo = processoRepository.findById(processoID).get();
        Documento documento = documentoRepository.findById(documentoID).get();
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        documentoSolicitado = documentoSolicitadoRepository.save(documentoSolicitado);

        return documentoSolicitado;
    }

    @Transactional
    public void desvincularDocumentoDoProcesso(Integer processoID, Integer documentoSolicitadoID) {
        Processo processo = processoRepository.findById(processoID).get();
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();

        if(documentoSolicitado.getProcesso().getId().equals(processo.getId())) {
            this.removerDocumentoSolicitadoEnviado(documentoSolicitado.getId());
            documentoSolicitadoRepository.deleteById(documentoSolicitadoID);
        } else {
            throw new DocumentoNotFoundException();
        }
    }

    @Transactional
    public void solicitarReenvioDocumentoSolicitado(Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        documentoSolicitado.solicitarReenvio();
    }

    @Transactional
    public DadoSolicitado vincularDadoAoProcesso(Integer processoID, Integer dadoID) {
        Processo processo = processoRepository.findById(processoID).get();
        Dado dado = dadoRepository.findById(dadoID).get();
        DadoSolicitado dadoSolicitado = new DadoSolicitado(dado, processo);

        dadoSolicitado = dadoSolicitadoRepository.save(dadoSolicitado);

        return dadoSolicitado;
    }

    public void desvincularDadoDoProcesso(Integer processoID, Integer dadoSolicitadoID) {
        Processo processo = processoRepository.findById(processoID).get();
        DadoSolicitado dadoSolicitado = dadoSolicitadoRepository.findById(dadoSolicitadoID).get();

        if(dadoSolicitado.getProcesso().getId().equals(processo.getId())) {
            dadoSolicitadoRepository.deleteById(dadoSolicitadoID);
        } else {
            throw new DadoNotFoundException();
        }
    }

    @Transactional
    public void atualizarStatusProcesso(Integer processoID, ProcessoDTO processoDTO) {
        Processo processo = processoRepository.findById(processoID).get();
        processo.atualizarStatus(processoDTO.status());
        
        notificarProspeccao(processo);
    }

    @Transactional
    public DocumentoSolicitado enviarDocumentoSolicitadoNoProcesso(Integer documentoSolicitadoID, MultipartFile documento) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        Processo processo = documentoSolicitado.getProcesso();
        String nomeArquivoOriginal = documento.getOriginalFilename();
        BigInteger tamanhoArquivo = BigInteger.valueOf(documento.getSize());

        if(!FilenameUtils.isExtension(nomeArquivoOriginal, "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        String pastaProcesso = "processos/"+ processo.getId() + "_" + processo.getCnpj() + "/";
        String extensaoArquivo = FilenameUtils.getExtension(nomeArquivoOriginal);
        String nomeDocumento = processo.getCnpj() + " - " + documentoSolicitado.getDocumento().getNome() + "." +  extensaoArquivo;
        String caminhoArquivo = pastaProcesso + nomeDocumento;

        if(!amazonS3Service.existeObjeto(pastaProcesso)) {
            amazonS3Service.criarPasta(pastaProcesso);
        }

        amazonS3Service.armazenarArquivo(documento, caminhoArquivo);

        documentoSolicitado.atualizar(caminhoArquivo, nomeArquivoOriginal, tamanhoArquivo);

        return documentoSolicitado;
    }

    public String baixarDocumentoSolicitadoNoProcesso(Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        String nomeArquivo = documentoSolicitado.getCaminhoS3();

        return amazonS3Service.recuperarArquivo(nomeArquivo);
    }

    @Transactional
    public DocumentoSolicitado removerDocumentoSolicitadoEnviado(Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        String pathArquivo = documentoSolicitado.getCaminhoS3();
        documentoSolicitado.removerEnvio();

        if(pathArquivo != null) {
            amazonS3Service.excluirArquivo(pathArquivo);
        }

        return documentoSolicitado;
    }

    private void notificarProspeccao(Processo processo) {
        Map<String, String> organizacao = new HashMap<>();
        organizacao.put("id", processo.getCadastroId().toString());
        organizacao.put("cnpj", processo.getCnpj());
        organizacao.put("nomeEmpresarial", processo.getNomeEmpresarial());
        organizacao.put("email", processo.getEmail());
        organizacao.put("statusCadastro", processo.getStatus().toString());

        rabbitTemplate.convertAndSend("compliance.ex", "", organizacao);
    }

    public List<Processo> consultarProcessosOrganizacao(Integer organizacaoId) {
        return processoRepository.findByCadastroId(organizacaoId, Sort.by(Sort.Direction.ASC, "status"));
    }
}
