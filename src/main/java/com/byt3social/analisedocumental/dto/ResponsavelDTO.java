package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResponsavelDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Campo inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Campo inválido")
        String telefone
) {
}
