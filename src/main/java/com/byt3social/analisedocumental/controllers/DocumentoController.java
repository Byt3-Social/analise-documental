package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import com.byt3social.analisedocumental.services.ProcessoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
public class DocumentoController {
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private ProcessoService processoService;

    @GetMapping("/documentos")
    public ResponseEntity consultarDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();

        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity consultarDocumento(@PathVariable("id") Integer documentoID) {
        Documento dado = documentoRepository.findById(documentoID).get();

        return new ResponseEntity<>(dado, HttpStatus.OK);
    }

    @PostMapping("/documentos")
    @Transactional
    public ResponseEntity cadastrarDocumento(@Valid @RequestBody DocumentoDTO documentoDTO) {
        Documento documento = new Documento(documentoDTO);
        documentoRepository.save(documento);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/documentos/{id}")
    @Transactional
    public ResponseEntity alterarDocumento(@PathVariable("id") Integer documentoID, @Valid @RequestBody DocumentoDTO documentoDTO) {
        Documento documento = documentoRepository.findById(documentoID).get();
        documento.atualizar(documentoDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity excluirDocumento(@PathVariable("id") Integer documentoID) {
        documentoRepository.deleteById(documentoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/documentos-solicitados/{id}/reenvio")
    public ResponseEntity solicitarReenvioDocumentoSolicitado(@PathVariable("id") Integer documentoSolicitadoID) {
        processoService.solicitarReenvioDocumentoSolicitado(documentoSolicitadoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/documentos-solicitados/aws-s3")
    public ResponseEntity enviarDocumentoSolicitado(@RequestParam MultipartFile documento, @RequestParam("documento_solicitado_id") Integer documentoSolicitadoID) {
        processoService.enviarDocumentoSolicitadoNoProcesso(documentoSolicitadoID, documento);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping( "/documentos-solicitados/{documento}/aws-s3")
    public ResponseEntity recuperarDocumentoSolicitado(@PathVariable("documento") Integer documentoSolicitradoID) {
        String urlArquivo = processoService.baixarDocumentoSolicitadoNoProcesso(documentoSolicitradoID);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlArquivo)).build();
    }

    @DeleteMapping("/documentos-solicitados/{documento}/aws-s3")
    public ResponseEntity removerDocumentoEnviado(@PathVariable("documento") Integer documentoSolicitadoID) {
        processoService.removerDocumentoSolicitadoEnviado(documentoSolicitadoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
