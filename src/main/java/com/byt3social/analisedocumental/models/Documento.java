package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity(name = "Documento")
@Table(name = "documentos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@DynamicInsert
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

    public void atualizaDocumento(DocumentoDTO body) {
        if(body.nome() != null) {
            this.nome = body.nome();
        }

        if(body.padrao() != null) {
            this.padrao = body.padrao();
        }
    }
}
