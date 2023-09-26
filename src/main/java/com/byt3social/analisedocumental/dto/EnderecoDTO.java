package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank(message = "endereço não pode ser vazio")
        String endereco,
        @NotBlank(message = "numero não pode ser vazio")
        String numero,
        @NotBlank(message = "bairro não pode ser vazio")
        String bairro,
        String complemento,
        @NotBlank(message = "cidade não pode ser vazio")
        String cidade,
        @NotBlank(message = "estado não pode ser vazio")
        String estado
) {
}
