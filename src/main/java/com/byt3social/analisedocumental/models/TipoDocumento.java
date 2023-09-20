package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "TipoDocumento")
@Table(name = "tipos_documentos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
}
