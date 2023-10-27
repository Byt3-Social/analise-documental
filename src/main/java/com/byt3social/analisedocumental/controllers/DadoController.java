package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.repositories.DadoRepository;

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
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/dados")
public class DadoController {
    @Autowired
    private DadoRepository dadoRepository;

    @Operation(summary = "Consultar todos os dados")
    @ApiResponse(responseCode = "200", description = "Dados consultados com sucesso", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping
    public ResponseEntity consultarDados() {
        List<Dado> dados = dadoRepository.findAll();

        return new ResponseEntity<>(dados, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um dado por ID")
    @ApiResponse(responseCode = "200", description = "Dado consultado com sucesso")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity consultarDado(@PathVariable("id") Integer dadoID) {
        Optional<Dado> optionalDado = dadoRepository.findById(dadoID);

        if (optionalDado.isPresent()) {
            Dado dado = optionalDado.get();
            return new ResponseEntity<>(dado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dado não encontrado", HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Cadastrar um novo dado")
    @ApiResponse(responseCode = "201", description = "Dado cadastrado com sucesso")
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarDado(@RequestBody @Valid DadoDTO dadoDTO) {
        Dado dado = new Dado(dadoDTO);
        dadoRepository.save(dado);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um dado existente")
    @ApiResponse(responseCode = "204", description = "Dado atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarDado(@PathVariable("id") Integer dadoID, @Valid @RequestBody DadoDTO dadoDTO) {
        Optional<Dado> optionalDado = dadoRepository.findById(dadoID);
        
        if (optionalDado.isPresent()) {
            Dado dado = optionalDado.get();
            dado.atualizar(dadoDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Excluir um dado por ID")
    @ApiResponse(responseCode = "200", description = "Dado excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity excluirDado(@PathVariable("id") Integer dadoID) {
        if (dadoRepository.existsById(dadoID)) {
            dadoRepository.deleteById(dadoID);

            return new ResponseEntity<>("Dado excluído com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dado não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
