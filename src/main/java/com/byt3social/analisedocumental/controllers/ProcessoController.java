package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.services.ProcessoService;
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
    public ResponseEntity listarProcessos() {
        List<Processo> processos = processoService.buscarProcessos();

        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @GetMapping("/processos/{id}")
    public ResponseEntity listaProcesso(@PathVariable Integer id) {
        Processo processo = processoService.buscaProcesso(id);

        return new ResponseEntity<>(processo, HttpStatus.OK);
    }

    @PostMapping("/processos")
    public ResponseEntity iniciarProcesso(@RequestBody ProcessoDTO dados, HttpServletRequest request) {
        processoService.criarProcesso(dados, request);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/processos/{id}")
    public ResponseEntity alteraProcesso(@PathVariable Integer id, @RequestBody ProcessoDTO dados) {
        processoService.alteraProcesso(id, dados);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
