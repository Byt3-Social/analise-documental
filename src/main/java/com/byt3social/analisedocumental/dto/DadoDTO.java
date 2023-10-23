package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadoDTO(
        @NotBlank(message = "nome não pode ser vazio")
        String nome,
        @NotBlank(message = "tipo não pode ser vazio")
        @Pattern(regexp = "TEXT|TEXTAREA|NUMBER|DATE", message = "tipo inválido")
        String tipo,
        @NotNull(message = "padrao não pode ser vazio")
        Boolean padrao
) {
}
