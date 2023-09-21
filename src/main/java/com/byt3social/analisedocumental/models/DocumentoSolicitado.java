package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "DocumentoSolicitado")
@Table(name = "documentos_solicitados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DocumentoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private StatusDocumentoSolicitado status;
    private String assinaturaDigital;
    private Boolean obrigatorio;
    @Column(name = "documento_id")
    private Integer documentoId;
    @Column(name = "processo_id")
    private Integer processoId;
}
