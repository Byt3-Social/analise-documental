package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Documento")
@Table(name = "documentos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Boolean padrao;
}
