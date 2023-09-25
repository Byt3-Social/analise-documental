package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.services.OrganizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrganizacaoController {
    @Autowired
    private OrganizacaoService organizacaoService;

    @PostMapping("/organizacoes/documentos")
    public ResponseEntity enviarDocumentoSolicitado(@RequestParam MultipartFile documento, @RequestParam("documento_solicitado_id") Integer documentoSolicitadoId) {
        organizacaoService.enviarDocumentoSolicitado(documentoSolicitadoId, documento);

        return new ResponseEntity(HttpStatus.OK);
    }
}
