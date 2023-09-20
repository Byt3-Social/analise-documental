package com.byt3social.analisedocumental.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Endereco {
    private String endereco;
    private String numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;
}
