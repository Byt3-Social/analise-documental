package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocumentoController {
    @Autowired
    private DocumentoRepository documentoRepository;

    @GetMapping("/documentos")
    public ResponseEntity listarDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();

        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity recuperaDocumento(@PathVariable Integer id) {
        Documento dado = documentoRepository.findById(id).get();

        return new ResponseEntity<>(dado, HttpStatus.OK);
    }

    @PostMapping("/documentos")
    @Transactional
    public ResponseEntity inserirNovoDocumento(@RequestBody DocumentoDTO body) {
        Documento dado = new Documento(body);
        documentoRepository.save(dado);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/documentos/{id}")
    @Transactional
    public ResponseEntity alterarDocumento(@PathVariable Integer id, @RequestBody DocumentoDTO body) {
        Documento dado = documentoRepository.findById(id).get();
        dado.atualizaDocumento(body);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity excluirDocumento(@PathVariable Integer id) {
        documentoRepository.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
