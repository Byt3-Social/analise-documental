package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.SocioDTO;
import com.byt3social.analisedocumental.exceptions.DadoNotFoundException;
import com.byt3social.analisedocumental.exceptions.DocumentoNotFoundException;
import com.byt3social.analisedocumental.exceptions.FileTypeNotSupportedException;
import com.byt3social.analisedocumental.models.*;
import com.byt3social.analisedocumental.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EmailService emailService;
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

        emailService.notificarOrganizacao(novoProcesso);
    }

    public List<Processo> consultarProcessos() {
        return processoRepository.findAll();
    }

    public Processo consultarProcesso(Integer processoID) {
        return processoRepository.findById(processoID).get();
    }

    @Transactional
    public Processo salvarProcesso(Integer processoID, ProcessoDTO processoDTO) {
        Processo processo = processoRepository.findById(processoID).get();
        List<DadoSolicitado> dadosSolicitados = dadoSolicitadoRepository.findByProcesso(processo);
        List<Socio> socios = socioRepository.findByProcesso(processo);

        if(processoDTO.dadosSolicitados() != null) {
            for(DadoSolicitado dadoSolicitado : dadosSolicitados) {
                DadoSolicitadoDTO dadoSolicitadoDTO = processoDTO.dadosSolicitados().stream().filter(dadoSolicitadoDTO1 -> dadoSolicitadoDTO1.id().equals(dadoSolicitado.getId())).findFirst().get();

                dadoSolicitado.atualizar(dadoSolicitadoDTO, processo);
            }
        }

        if(processoDTO.socios() != null) {
            for(SocioDTO socioDTO : processoDTO.socios()) {
                socios.stream().filter(socio -> socio.getId().equals(socioDTO.id())).findFirst().ifPresentOrElse(socio -> socio.atualizar(socioDTO, processo), () -> {
                    Socio novoSocio = new Socio(socioDTO, processo);
                    socios.add(novoSocio);
                });
            }

            socioRepository.saveAll(socios);
        }

        processo.atualizar(processoDTO);

        return processo;
    }

    @Transactional
    public void vincularDocumentoAoProcesso(Integer processoID, Integer documentoID) {
        Processo processo = processoRepository.findById(processoID).get();
        Documento documento = documentoRepository.findById(documentoID).get();
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        documentoSolicitadoRepository.save(documentoSolicitado);
    }

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
    public void vincularDadoAoProcesso(Integer processoID, Integer dadoID) {
        Processo processo = processoRepository.findById(processoID).get();
        Dado dado = dadoRepository.findById(dadoID).get();
        DadoSolicitado dadoSolicitado = new DadoSolicitado(dado, processo);

        dadoSolicitadoRepository.save(dadoSolicitado);
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

    public void atualizarStatusProcesso(Integer processoID, ProcessoDTO processoDTO) {
        Processo processo = this.salvarProcesso(processoID, processoDTO);
        
        notificarProspeccao(processo);
    }

    @Transactional
    public void enviarDocumentoSolicitadoNoProcesso(Integer documentoSolicitadoID, MultipartFile documento) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        Processo processo = documentoSolicitado.getProcesso();
        String nomeArquivoOriginal = documento.getOriginalFilename();
        BigInteger tamanhoArquivo = BigInteger.valueOf(documento.getSize());

        if(!FilenameUtils.isExtension(nomeArquivoOriginal, "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        String pastaProcesso = processo.getId() + "_" + processo.getCnpj() + "/";
        String extensaoArquivo = FilenameUtils.getExtension(nomeArquivoOriginal);
        String nomeDocumento = processo.getCnpj() + " - " + documentoSolicitado.getDocumento().getNome() + "." +  extensaoArquivo;
        String caminhoArquivo = pastaProcesso + nomeDocumento;

        if(!amazonS3Service.existeObjeto(pastaProcesso)) {
            amazonS3Service.criarPasta(pastaProcesso);
        }

        amazonS3Service.armazenarArquivo(documento, caminhoArquivo);

        documentoSolicitado.atualizar(caminhoArquivo, nomeArquivoOriginal, tamanhoArquivo);
    }

    public String baixarDocumentoSolicitadoNoProcesso(Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        String nomeArquivo = documentoSolicitado.getCaminhoS3();

        return amazonS3Service.recuperarArquivo(nomeArquivo);
    }

    @Transactional
    public void removerDocumentoSolicitadoEnviado(Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoID).get();
        String pathArquivo = documentoSolicitado.getCaminhoS3();
        documentoSolicitado.removerEnvio();

        amazonS3Service.excluirArquivo(pathArquivo);
    }

    private void notificarProspeccao(Processo processo) {
        Map<String, String> organizacao = new HashMap<>();
        organizacao.put("id", processo.getCadastroId().toString());
        organizacao.put("status_cadastro", processo.getStatus().toString());
        System.out.println(organizacao);

        rabbitTemplate.convertAndSend("compliance.ex", "processo.atualizado", organizacao);
    }
}
