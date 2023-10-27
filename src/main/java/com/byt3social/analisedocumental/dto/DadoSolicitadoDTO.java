package com.byt3social.analisedocumental.dto;

import jakarta.validation.constraints.NotBlank;

public record DadoSolicitadoDTO(
        Integer id,
        @NotBlank(message = "campo não pode ser vazio")
        String valor
) { }
