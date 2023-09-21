package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity(name = "Dado")
@Table(name = "dados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@DynamicInsert
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

    public void atualizaDado(DadoDTO body) {
        if(body.nome() != null) {
            this.nome = body.nome();
        }

        if(body.tipo() != null) {
            this.tipo = body.tipo();
        }

        if(body.padrao() != null) {
            this.padrao = body.padrao();
        }
    }
}
