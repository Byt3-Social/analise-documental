package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Responsavel {
    @Column(name = "nome_responsavel")
    private String nome;
    @Column(name = "cpf_responsavel")
    private String cpf;
    @Column(name = "email_responsavel")
    private String email;
    @Column(name = "telefone_responsavel")
    private String telefone;

    public Responsavel(ResponsavelDTO responsavel) {
        this.nome = responsavel.nome();
        this.email = responsavel.email();
        this.telefone = responsavel.telefone();
        this.cpf = responsavel.cpf();
    }
}
