package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank(message = "Campo obrigatório")
        String endereco,
        @NotBlank(message = "Campo obrigatório")
        String numero,
        @NotBlank(message = "Campo obrigatório")
        String bairro,
        String complemento,
        @NotBlank(message = "Campo obrigatório")
        String cidade,
        @NotBlank(message = "Campo obrigatório")
        String estado
) {
}
