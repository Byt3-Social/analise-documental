package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import com.byt3social.analisedocumental.services.ProcessoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
public class DocumentoController {
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private ProcessoService processoService;

    @Operation(summary = "Consultar todos os documentos")
    @ApiResponse(responseCode = "200", description = "Documentos consultados com sucesso", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/documentos")
    public ResponseEntity consultarDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();

        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um documento por ID")
    @ApiResponse(responseCode = "200", description = "Documento consultado com sucesso")
    @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    @GetMapping("/documentos/{id}")
    public ResponseEntity consultarDocumento(@PathVariable("id") Integer documentoID) {
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoID);

        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();
            return new ResponseEntity<>(documento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Cadastrar um novo documento")
    @ApiResponse(responseCode = "201", description = "Documento cadastrado com sucesso")
    @PostMapping("/documentos")
    @Transactional
    public ResponseEntity cadastrarDocumento(@Valid @RequestBody DocumentoDTO documentoDTO) {
        Documento documento = new Documento(documentoDTO);
        documentoRepository.save(documento);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Alterar um documento existente")
    @ApiResponse(responseCode = "204", description = "Documento atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    @PutMapping("/documentos/{id}")
    @Transactional
    public ResponseEntity alterarDocumento(@PathVariable("id") Integer documentoID, @Valid @RequestBody DocumentoDTO documentoDTO) {
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoID);

        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();
            documento.atualizar(documentoDTO);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Excluir um documento por ID")
    @ApiResponse(responseCode = "200", description = "Documento excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    @DeleteMapping("/documentos/{id}")
    public ResponseEntity excluirDocumento(@PathVariable("id") Integer documentoID) {
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoID);

        if (documentoOptional.isPresent()) {
            documentoRepository.deleteById(documentoID);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Solicitar reenvio de um documento solicitado")
    @ApiResponse(responseCode = "204", description = "Reenvio solicitado com sucesso")
    @PostMapping("/documentos-solicitados/{id}/reenvio")
    public ResponseEntity solicitarReenvioDocumentoSolicitado(@PathVariable("id") Integer documentoSolicitadoID) {
        processoService.solicitarReenvioDocumentoSolicitado(documentoSolicitadoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enviar documento solicitado no processo")
    @ApiResponse(responseCode = "200", description = "Documento enviado com sucesso")
    @PostMapping("/documentos-solicitados/{id}/aws")
    public ResponseEntity enviarDocumentoSolicitado(@RequestParam MultipartFile documento, @PathVariable("id") Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = processoService.enviarDocumentoSolicitadoNoProcesso(documentoSolicitadoID, documento);

        return new ResponseEntity(documentoSolicitado, HttpStatus.OK);
    }

    @Operation(summary = "Recuperar documento solicitado no processo")
    @ApiResponse(responseCode = "200", description = "Documento recuperado com sucesso")
    @GetMapping( "/documentos-solicitados/{id}/aws")
    public ResponseEntity recuperarDocumentoSolicitado(@PathVariable("id") Integer documentoSolicitradoID) {
        String urlArquivo = processoService.baixarDocumentoSolicitadoNoProcesso(documentoSolicitradoID);

        return new ResponseEntity(urlArquivo, HttpStatus.OK);
    }

    @Operation(summary = "Remover documento solicitado enviado")
    @ApiResponse(responseCode = "200", description = "Documento removido com sucesso")
    @DeleteMapping("/documentos-solicitados/{id}/aws")
    public ResponseEntity removerDocumentoEnviado(@PathVariable("id") Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = processoService.removerDocumentoSolicitadoEnviado(documentoSolicitadoID);

        return new ResponseEntity(documentoSolicitado, HttpStatus.OK);
    }
}
