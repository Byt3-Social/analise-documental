package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Socio")
@Table(name = "socios")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String qualificacao;
    private Integer processoId;
}
