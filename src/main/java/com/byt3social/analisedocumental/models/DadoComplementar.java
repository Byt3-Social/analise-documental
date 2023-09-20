package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "DadoComplementar")
@Table(name = "dados_complementares")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DadoComplementar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valor;
    private Integer processoId;
    private Integer tipoId;
}
