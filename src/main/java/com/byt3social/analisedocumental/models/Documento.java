package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
    private String url;
    private StatusDocumento status;
    private String assinaturaDigital;
    private Integer tipoDocumentoId;
    private Integer processoId;
}
