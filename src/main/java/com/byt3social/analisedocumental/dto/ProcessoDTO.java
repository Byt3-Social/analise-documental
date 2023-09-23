package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public record ProcessoDTO(
        Integer id,
        String cnpj,
        @JsonProperty("data_abertura")
        Date dataAbertura,
        @JsonProperty("nome_empresarial")
        String nomeEmpresarial,
        @JsonProperty("nome_fantasia")
        String nomeFantasia,
        EnderecoDTO endereco,
        String porte,
        String email,
        String telefone,
        @JsonProperty("data_criacao")
        Timestamp dataCriacao,
        @JsonProperty("data_atualizacao")
        Timestamp dataAtualizacao,
        StatusProcesso status,
        ResponsavelDTO responsavel,
        String link,
        String feedback,
        @JsonProperty("socios")
        List<SocioDTO> socios,
        @JsonProperty("documentos_solicitados")
        List<DocumentoSolicitadoDTO> documentosSolicitados,
        @JsonProperty("dados_solicitados")
        List<DadoSolicitadoDTO> dadosSolicitados
) {
}
