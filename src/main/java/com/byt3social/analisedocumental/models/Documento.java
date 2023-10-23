package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Documento")
@Table(name = "documentos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
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
        this.nome = documentoDTO.nome();
        this.padrao = documentoDTO.padrao();
    }
}
