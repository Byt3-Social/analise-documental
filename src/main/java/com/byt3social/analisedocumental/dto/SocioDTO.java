package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;

public record SocioDTO(
        Integer id,
        @NotBlank(message = "nome do sócio não pode ser vazio")
        String nome,
        @NotBlank(message = "cpf do sócio não pode ser vazio")
        String cpf,
        @NotBlank(message = "qualificação do sócio não pode ser vazio")
        String qualificacao

) { }
