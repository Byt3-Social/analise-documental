package com.byt3social.analisedocumental.dto;

public record OrganizacaoDTO(
        Integer id,
        String cnpj,
        String nome,
        String email,
        String telefone,
        ResponsavelDTO responsavel
) {
}
