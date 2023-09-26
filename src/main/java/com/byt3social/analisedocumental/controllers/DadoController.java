package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.repositories.DadoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity consultarDados() {
        List<Dado> dados = dadoRepository.findAll();

        return new ResponseEntity<>(dados, HttpStatus.OK);
    }

    @GetMapping("/dados/{id}")
    public ResponseEntity consultarDado(@PathVariable("id") Integer dadoID) {
        Dado dado = dadoRepository.findById(dadoID).get();

        return new ResponseEntity<>(dado, HttpStatus.OK);
    }

    @PostMapping("/dados")
    @Transactional
    public ResponseEntity cadastrarDado(@RequestBody @Valid DadoDTO dadoDTO) {
        Dado dado = new Dado(dadoDTO);
        dadoRepository.save(dado);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/dados/{id}")
    @Transactional
    public ResponseEntity atualizarDado(@PathVariable("id") Integer dadoID, @Valid @RequestBody DadoDTO dadoDTO) {
        Dado dado = dadoRepository.findById(dadoID).get();
        dado.atualizar(dadoDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/dados/{id}")
    public ResponseEntity excluirDado(@PathVariable("id") Integer dadoID) {
        dadoRepository.deleteById(dadoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
