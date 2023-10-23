package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import com.byt3social.analisedocumental.services.ProcessoService;
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

    @GetMapping("/documentos")
    public ResponseEntity consultarDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();

        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

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
        Optional<Documento> documentoOptional = documentoRepository.findById(documentoID);

        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();
            documento.atualizar(documentoDTO);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


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

    @PostMapping("/documentos-solicitados/{id}/reenvio")
    public ResponseEntity solicitarReenvioDocumentoSolicitado(@PathVariable("id") Integer documentoSolicitadoID) {
        processoService.solicitarReenvioDocumentoSolicitado(documentoSolicitadoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/documentos-solicitados/{id}/aws")
    public ResponseEntity enviarDocumentoSolicitado(@RequestParam MultipartFile documento, @PathVariable("id") Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = processoService.enviarDocumentoSolicitadoNoProcesso(documentoSolicitadoID, documento);

        return new ResponseEntity(documentoSolicitado, HttpStatus.OK);
    }

    @GetMapping( "/documentos-solicitados/{id}/aws")
    public ResponseEntity recuperarDocumentoSolicitado(@PathVariable("id") Integer documentoSolicitradoID) {
        String urlArquivo = processoService.baixarDocumentoSolicitadoNoProcesso(documentoSolicitradoID);

        return new ResponseEntity(urlArquivo, HttpStatus.OK);
    }

    @DeleteMapping("/documentos-solicitados/{id}/aws")
    public ResponseEntity removerDocumentoEnviado(@PathVariable("id") Integer documentoSolicitadoID) {
        DocumentoSolicitado documentoSolicitado = processoService.removerDocumentoSolicitadoEnviado(documentoSolicitadoID);

        return new ResponseEntity(documentoSolicitado, HttpStatus.OK);
    }
}
