package com.byt3social.analisedocumental.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Responsavel {
    private String nome;
    private String email;
    private String telefone;
}
