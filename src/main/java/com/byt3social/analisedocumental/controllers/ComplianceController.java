package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.services.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ComplianceController {
    @Autowired
    private ComplianceService complianceService;

    @GetMapping( "/organizacoes/documentos/{documento}")
    public ResponseEntity recuperarDocumentoSolicitado(@PathVariable("documento") Integer documentoId) {
        String urlArquivo = complianceService.baixarDocumentoSolicitado(documentoId);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlArquivo)).build();
    }

    @DeleteMapping("/organizacoes/documentos/{id}")
    public ResponseEntity excluirDocumentoEnviado(@PathVariable("id") Integer documentoEnviadoId) {
        complianceService.excluirDocumentoSolicitado(documentoEnviadoId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
