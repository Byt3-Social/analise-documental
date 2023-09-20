package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "TipoDadoComplementar")
@Table(name = "tipos_dados_complementares")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoDadoComplementar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;
}
