package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public record ProcessoDTO(
        Integer id,
        @JsonProperty("cadastro_id")
        Integer cadastroId,
        @NotBlank(message = "cnpj não pode ser vazio")
        @Pattern(regexp = "\\d{14}", message = "cnpj inválido")
        String cnpj,
        @JsonProperty("data_abertura")
        @NotNull(message = "data de abertura não pode ser vazio")
        Date dataAbertura,
        @JsonProperty("nome_empresarial")
        @NotBlank(message = "nome empresarial não pode ser vazio")
        String nomeEmpresarial,
        @JsonProperty("nome_fantasia")
        @NotBlank(message = "nome fantasia não pode ser vazio")
        String nomeFantasia,
        @NotNull(message = "endereco não pode ser vazio")
        @Valid
        EnderecoDTO endereco,
        @NotBlank(message = "porte não pode ser vazio")
        String porte,
        @NotBlank(message = "email não pode ser vazio")
        @Email(message = "email inválido")
        String email,
        @NotBlank(message = "telefone não pode ser vazio")
        @Pattern(regexp = "\\d{10,11}", message = "telefone inválido")
        String telefone,
        @JsonProperty("created_at")
        Timestamp createdAt,
        @JsonProperty("updated_at")
        Timestamp updatedAt,
        StatusProcesso status,
        @NotNull(message = "responsavel não pode ser vazio")
        @Valid
        ResponsavelDTO responsavel,
        String uuid,
        String feedback,
        @JsonProperty("socios")
        @NotNull(message = "socios não pode ser vazio")
        @NotEmpty(message = "deve haver pelo menos um sócio vinculado a organização")
        List<@Valid SocioDTO> socios,
        @JsonProperty("documentos_solicitados")
        List<DocumentoSolicitadoDTO> documentosSolicitados,
        @JsonProperty("dados_solicitados")
        List<@Valid DadoSolicitadoDTO> dadosSolicitados
) {
}
