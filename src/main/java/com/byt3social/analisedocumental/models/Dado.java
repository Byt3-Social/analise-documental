package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Dado")
@Table(name = "dados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Dado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;
    private Boolean padrao;

    public Dado(DadoDTO body) {
        this.nome = body.nome();
        this.tipo = body.tipo();
        this.padrao = body.padrao();
    }

    public void atualizar(DadoDTO dadoDTO) {
        if(dadoDTO.nome() != null) {
            this.nome = dadoDTO.nome();
        }

        if(dadoDTO.tipo() != null) {
            this.tipo = dadoDTO.tipo();
        }

        if(dadoDTO.padrao() != null) {
            this.padrao = dadoDTO.padrao();
        }
    }
    
}
