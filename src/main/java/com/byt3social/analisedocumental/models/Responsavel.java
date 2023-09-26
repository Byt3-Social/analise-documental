package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Responsavel {
    @Column(name = "nome_responsavel")
    private String nome;
    @Column(name = "email_responsavel")
    private String email;
    @Column(name = "telefone_responsavel")
    private String telefone;

    public Responsavel(ResponsavelDTO responsavel) {
        if(responsavel.nome() != null) {
            this.nome = responsavel.nome();
        }

        if(responsavel.email() != null) {
            this.email = responsavel.email();
        }

        if(responsavel.telefone() != null) {
            this.telefone = responsavel.telefone();
        }
    }
}
