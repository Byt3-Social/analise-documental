package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.EnderecoDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class Endereco {
    private String endereco;
    private String numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;

    public Endereco() {
        this.endereco = "";
        this.numero = "";
        this.bairro = "";
        this.complemento = "";
        this.cidade = "";
        this.estado = "";
    }

    public Endereco(EnderecoDTO endereco) {
        this.endereco = endereco.endereco();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.cidade = endereco.cidade();
        this.estado = endereco.estado();
        this.complemento = endereco.complemento();
    }
}
