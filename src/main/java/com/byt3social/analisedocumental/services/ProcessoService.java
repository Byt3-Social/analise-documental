package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.byt3social.analisedocumental.models.*;
import com.byt3social.analisedocumental.repositories.DadoRepository;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import com.byt3social.analisedocumental.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DocumentoRepository documentoRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void criarProcesso(OrganizacaoDTO organizacao) {
        Processo novoProcesso = new Processo(organizacao);

        List<Dado> dados = dadoRepository.findDadoByPadrao(true);
        List<Documento> documentos = documentoRepository.findDocumentoByPadrao(true);

        List<DocumentoSolicitado> documentoSolicitados = new ArrayList<>();
        List<DadoSolicitado> dadosSolicitados = new ArrayList<>();

        for (Documento documento : documentos) {
            documentoSolicitados.add(new DocumentoSolicitado(documento, novoProcesso));
        }

        for (Dado dado : dados) {
            dadosSolicitados.add(new DadoSolicitado(dado, novoProcesso));
        }

        novoProcesso.adicionaDadosSolicitados(dadosSolicitados);
        novoProcesso.adicionaDocumentosSolicitados(documentoSolicitados);

        processoRepository.save(novoProcesso);

        Map<String, String> org = new HashMap<>();
        org.put("cnpj", novoProcesso.getCnpj());
        org.put("status_cadastro", StatusProcesso.EM_ANALISE.toString());

        System.out.println(org.get("status_cadastro"));

        rabbitTemplate.convertAndSend("processos.ex", "processo.aberto", org);

        emailService.notificaEmpresa(novoProcesso);
    }

    public List<Processo> buscarProcessos() {
        return processoRepository.findAll();
    }

    public Processo buscaProcesso(Integer id) {
        Processo processo = processoRepository.findById(id).get();
        System.out.println(processo.getDocumentosSolicitados().get(0).getDocumento().getNome());
        return processo;
    }

    @Transactional
    public void alteraProcesso(Integer id, ProcessoDTO dados) {
        Processo processo = processoRepository.findById(id).get();
        List<Dado> listaDados = dadoRepository.findAll();
        List<Documento> listaDocumentos = documentoRepository.findAll();

        processo.atualizaProcesso(dados, listaDados, listaDocumentos);
    }
}
