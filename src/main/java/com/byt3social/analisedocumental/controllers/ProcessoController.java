package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.PDSignProcessoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.services.ProcessoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {
    @Autowired
    private ProcessoService processoService;

    @Operation(summary = "Consultar todos os processos")
    @ApiResponse(responseCode = "200", description = "Processos consultados com sucesso", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping    
    public ResponseEntity consultarProcessos() {
        List<Processo> processos = processoService.consultarProcessos();

        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar processos por organização")
    @ApiResponse(responseCode = "200", description = "Processos consultados com sucesso", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/organizacoes")
    public ResponseEntity consultarProcessosOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<Processo> processos = processoService.consultarProcessosOrganizacao(Integer.valueOf(organizacaoId));

        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um processo por ID")
    @ApiResponse(responseCode = "200", description = "Processo consultado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity consultarProcesso(@PathVariable("id") Integer processoID) {
        Processo processo = processoService.consultarProcesso(processoID);

        return new ResponseEntity<>(processo, HttpStatus.OK);
    }

    @Operation(summary = "Consultar processos PDSign por ID")
    @ApiResponse(responseCode = "200", description = "Processos PDSign consultados com sucesso", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/{id}/pdsign")
    public ResponseEntity consultarProcessoPDSign(@PathVariable("id") Integer processoID) {
        List<PDSignProcessoDTO> pdSignProcessoDTO = processoService.consultarProcessoPDSign(processoID);

        return new ResponseEntity<>(pdSignProcessoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Abrir um novo processo")
    @ApiResponse(responseCode = "201", description = "Processo aberto com sucesso")
    @PostMapping
    public ResponseEntity abrirProcesso(@RequestBody OrganizacaoDTO organizacaoDTO) {
        processoService.abrirProcesso(organizacaoDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Salvar processo")
    @ApiResponse(responseCode = "204", description = "Processo salvo com sucesso")
    @PutMapping("/{id}/atualizar")
    public ResponseEntity salvarProcesso(@PathVariable("id") Integer processoID, @RequestBody ProcessoDTO processoDTO) {
        processoService.salvarProcesso(processoID, processoDTO, false);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Finalizar processo")
    @ApiResponse(responseCode = "204", description = "Processo finalizado com sucesso")
    @PutMapping("/{id}/finalizar")
    public ResponseEntity submeterProcesso(@PathVariable("id") Integer processoID, @Valid @RequestBody ProcessoDTO processoDTO) {
        processoService.salvarProcesso(processoID, processoDTO, true);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Solicitar documento ao processo")
    @ApiResponse(responseCode = "204", description = "Documento solicitado com sucesso")
    @PostMapping("/{id}/documentos/{documento}")
    public ResponseEntity solicitarDocumento(@PathVariable("id") Integer processoID, @PathVariable("documento") Integer documentoID) {
        DocumentoSolicitado documentoSolicitado = processoService.vincularDocumentoAoProcesso(processoID, documentoID);

        return new ResponseEntity<>(documentoSolicitado, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Excluir documento solicitado do processo")
    @ApiResponse(responseCode = "204", description = "Documento solicitado excluído com sucesso")
    @DeleteMapping("/{id}/documentos-solicitados/{documento}")
    public ResponseEntity excluirDocumentoSolicitado(@PathVariable("id") Integer processoID, @PathVariable("documento") Integer documentoSolicitadoID) {
        processoService.desvincularDocumentoDoProcesso(processoID, documentoSolicitadoID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Solicitar dado ao processo")
    @ApiResponse(responseCode = "204", description = "Dado solicitado com sucesso")
    @PostMapping("/{id}/dados/{dado}")
    public ResponseEntity solicitarDado(@PathVariable("id") Integer processoID, @PathVariable("dado") Integer dadoID) {
        DadoSolicitado dadoSolicitado = processoService.vincularDadoAoProcesso(processoID, dadoID);

        return new ResponseEntity<>(dadoSolicitado, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Excluir dado solicitado do processo")
    @ApiResponse(responseCode = "204", description = "Dado solicitado excluído com sucesso")
    @DeleteMapping("/{id}/dados-solicitados/{dado}")
    public ResponseEntity excluirDadoSolicitado(@PathVariable("id") Integer processoID, @PathVariable("dado") Integer dadoSolicitadoID) {
        processoService.desvincularDadoDoProcesso(processoID, dadoSolicitadoID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Atualizar status do processo")
    @ApiResponse(responseCode = "204", description = "Status do processo atualizado com sucesso")
    @PutMapping("/{id}/status")
    public ResponseEntity atualizarStatusProcesso(@PathVariable("id") Integer processoID, @RequestBody ProcessoDTO processoDTO) {
        processoService.atualizarStatusProcesso(processoID, processoDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
