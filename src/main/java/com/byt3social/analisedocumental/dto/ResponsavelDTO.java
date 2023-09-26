package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResponsavelDTO(
        @NotBlank(message = "nome do responsável não pode ser vazio")
        String nome,
        @NotBlank(message = "email do responsável não pode ser vazio")
        @Email(message = "email inválido")
        String email,
        @NotBlank(message = "telefone não pode ser vazio")
        @Pattern(regexp = "\\d{10,11}", message = "telefone inválido")
        String telefone
) {
}
