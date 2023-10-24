package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.PDSignProcessoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.services.ProcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProcessoController {
    @Autowired
    private ProcessoService processoService;

    @GetMapping("/processos")
    public ResponseEntity consultarProcessos() {
        List<Processo> processos = processoService.consultarProcessos();

        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @GetMapping("/processos/organizacoes")
    public ResponseEntity consultarProcessosOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<Processo> processos = processoService.consultarProcessosOrganizacao(Integer.valueOf(organizacaoId));

        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @GetMapping("/processos/{id}")
    public ResponseEntity consultarProcesso(@PathVariable("id") Integer processoID) {
        Processo processo = processoService.consultarProcesso(processoID);

        return new ResponseEntity<>(processo, HttpStatus.OK);
    }

    @GetMapping("/processos/{id}/pdsign")
    public ResponseEntity consultarProcessoPDSign(@PathVariable("id") Integer processoID) {
        List<PDSignProcessoDTO> pdSignProcessoDTO = processoService.consultarProcessoPDSign(processoID);

        return new ResponseEntity<>(pdSignProcessoDTO, HttpStatus.OK);
    }

    @PostMapping("/processos")
    public ResponseEntity abrirProcesso(@RequestBody OrganizacaoDTO organizacaoDTO) {
        processoService.abrirProcesso(organizacaoDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/processos/{id}/atualizar")
    public ResponseEntity salvarProcesso(@PathVariable("id") Integer processoID, @RequestBody ProcessoDTO processoDTO) {
        processoService.salvarProcesso(processoID, processoDTO, false);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/processos/{id}/finalizar")
    public ResponseEntity submeterProcesso(@PathVariable("id") Integer processoID, @Valid @RequestBody ProcessoDTO processoDTO) {
        processoService.salvarProcesso(processoID, processoDTO, true);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/processos/{id}/documentos/{documento}")
    public ResponseEntity solicitarDocumento(@PathVariable("id") Integer processoID, @PathVariable("documento") Integer documentoID) {
        DocumentoSolicitado documentoSolicitado = processoService.vincularDocumentoAoProcesso(processoID, documentoID);

        return new ResponseEntity<>(documentoSolicitado, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/processos/{id}/documentos-solicitados/{documento}")
    public ResponseEntity excluirDocumentoSolicitado(@PathVariable("id") Integer processoID, @PathVariable("documento") Integer documentoSolicitadoID) {
        processoService.desvincularDocumentoDoProcesso(processoID, documentoSolicitadoID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/processos/{id}/dados/{dado}")
    public ResponseEntity solicitarDado(@PathVariable("id") Integer processoID, @PathVariable("dado") Integer dadoID) {
        DadoSolicitado dadoSolicitado = processoService.vincularDadoAoProcesso(processoID, dadoID);

        return new ResponseEntity<>(dadoSolicitado, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/processos/{id}/dados-solicitados/{dado}")
    public ResponseEntity excluirDadoSolicitado(@PathVariable("id") Integer processoID, @PathVariable("dado") Integer dadoSolicitadoID) {
        processoService.desvincularDadoDoProcesso(processoID, dadoSolicitadoID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/processos/{id}/status")
    public ResponseEntity atualizarStatusProcesso(@PathVariable("id") Integer processoID, @RequestBody ProcessoDTO processoDTO) {
        processoService.atualizarStatusProcesso(processoID, processoDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
