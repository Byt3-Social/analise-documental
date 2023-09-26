package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Date;

public record DocumentoSolicitadoDTO(
        Integer id,
        String caminhoS3,
        String nomeArquivoOriginal,
        @JsonProperty("created_at")
        Date createdAt,
        @JsonProperty("updated_at")
        Date updatedAt,
        @JsonProperty("tamanho_arquivo")
        BigInteger tamanhoArquivo,
        StatusDocumentoSolicitado status,
        @JsonProperty("assinatura_digital")
        String assinaturaDigital,
        Boolean obrigatorio,
        DocumentoDTO documento
) { }
