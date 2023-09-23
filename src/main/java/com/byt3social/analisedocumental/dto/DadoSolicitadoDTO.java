package com.byt3social.analisedocumental.dto;

public record DadoSolicitadoDTO(
        Integer id,
        String valor,
        Boolean obrigatorio,
        DadoDTO dado
) { }
