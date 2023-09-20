package com.byt3social.analisedocumental.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Responsavel {
    @Column(name = "nome_responsavel")
    private String nome;
    @Column(name = "email_responsavel")
    private String email;
    @Column(name = "telefone_responsavel")
    private String telefone;
}
