package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.repositories.DadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DadoController {
    @Autowired
    private DadoRepository dadoRepository;

    @GetMapping("/dados")
    public ResponseEntity listarDados() {
        List<Dado> dados = dadoRepository.findAll();

        return new ResponseEntity<>(dados, HttpStatus.OK);
    }

    @GetMapping("/dados/{id}")
    public ResponseEntity recuperaDado(@PathVariable Integer id) {
        Dado dado = dadoRepository.findById(id).get();

        return new ResponseEntity<>(dado, HttpStatus.OK);
    }

    @PostMapping("/dados")
    @Transactional
    public ResponseEntity inserirNovoDado(@RequestBody DadoDTO body) {
        Dado dado = new Dado(body);
        dadoRepository.save(dado);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/dados/{id}")
    @Transactional
    public ResponseEntity alterarDado(@PathVariable Integer id, @RequestBody DadoDTO body) {
        Dado dado = dadoRepository.findById(id).get();
        dado.atualizaDado(body);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/dados/{id}")
    public ResponseEntity excluirDado(@PathVariable Integer id) {
        dadoRepository.deleteById(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
