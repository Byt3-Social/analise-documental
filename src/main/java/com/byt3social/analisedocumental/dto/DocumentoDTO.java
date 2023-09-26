package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentoDTO(
        Integer id,
        @NotBlank(message = "nome não pode ser vazio")
        String nome,
        @NotNull(message = "padrão não pode ser vazio")
        Boolean padrao
) {
}
