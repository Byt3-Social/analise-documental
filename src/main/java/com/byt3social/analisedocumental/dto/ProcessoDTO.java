package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusProcesso;

import java.sql.Timestamp;
import java.util.List;

public record ProcessoDTO(
        Integer id,
        String cnpj,
        String dataAbertura,
        String nomeEmpresarial,
        String nomeFantasia,
        String porte,
        String email,
        String telefone,
        Timestamp dataCriacao,
        Timestamp dataAtualizacao,
        StatusProcesso status,
        ResponsavelDTO responsavelDTO,
        String link,
        String feedback,
        List<SocioDTO> sociosDTO,
        List<DocumentoSolicitadoDTO> documentosSolicitadosDTO,
        List<DadoSolicitadoDTO> dadosSolicitadosDTO
) {
}
