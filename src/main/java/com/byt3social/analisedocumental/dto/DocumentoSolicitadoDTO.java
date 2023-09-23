package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DocumentoSolicitadoDTO(
        Integer id,
        String url,
        StatusDocumentoSolicitado status,
        @JsonProperty("assinatura_digital")
        String assinaturaDigital,
        Boolean obrigatorio,
        DocumentoDTO documento
) { }
