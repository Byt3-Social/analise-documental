package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Dado")
@Table(name = "dados")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
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
        this.nome = dadoDTO.nome();
        this.tipo = dadoDTO.tipo();
        this.padrao = dadoDTO.padrao();
    }
    
}
