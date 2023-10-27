package com.byt3social.analisedocumental.dto;

import com.byt3social.analisedocumental.enums.StatusProcesso;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

public record ProcessoDTO(
        @NotNull(message = "Campo obrigatório")
        LocalDate dataAbertura,
        @NotBlank(message = "Campo obrigatório")
        String nomeEmpresarial,
        @NotBlank(message = "Campo obrigatório")
        String nomeFantasia,
        @NotNull
        @Valid
        EnderecoDTO endereco,
        @NotBlank(message = "Campo obrigatório")
        String porte,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Campo inválido")
        String email,
        @Pattern(regexp = "\\d{10,11}", message = "Campo inválido")
        String telefone,
        StatusProcesso status,
        @NotNull
        @Valid
        ResponsavelDTO responsavel,
        String uuid,
        String feedback,
        List<SocioDTO> socios,
        List<DadoSolicitadoDTO> dadosSolicitados
) {
}
