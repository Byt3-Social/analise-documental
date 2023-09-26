package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Documento")
@Table(name = "documentos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Boolean padrao;

    public Documento(DocumentoDTO body) {
        this.nome = body.nome();
        this.padrao = body.padrao();
    }

    public void atualizar(DocumentoDTO documentoDTO) {
        if(documentoDTO.nome() != null) {
            this.nome = documentoDTO.nome();
        }

        if(documentoDTO.padrao() != null) {
            this.padrao = documentoDTO.padrao();
        }
    }
}
