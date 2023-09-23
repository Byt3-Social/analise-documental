package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.EnderecoDTO;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Getter
public class Endereco {
    private String endereco;
    private String numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String estado;

    public Endereco(EnderecoDTO endereco) {
        if(endereco.endereco() != null) {
            this.endereco = endereco.endereco();
        }

        if(endereco.numero() != null) {
            this.numero = endereco.numero();
        }

        if(endereco.bairro() != null) {
            this.bairro = endereco.bairro();
        }

        if(endereco.cidade() != null) {
            this.cidade = endereco.cidade();
        }

        if(endereco.estado() != null) {
            this.estado = endereco.estado();
        }

        if(endereco.complemento() != null) {
            this.complemento = endereco.complemento();
        }
    }
}
