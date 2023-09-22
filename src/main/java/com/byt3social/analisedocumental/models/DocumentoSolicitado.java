package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "DocumentoSolicitado")
@Table(name = "documentos_solicitados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class DocumentoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    @Enumerated(value = EnumType.STRING)
    private StatusDocumentoSolicitado status;
    private String assinaturaDigital;
    private Boolean obrigatorio;
    @ManyToOne
    @JoinColumn(name = "documento_id")
    private Documento documento;
    @ManyToOne
    @JoinColumn(name = "processo_id")
    @JsonBackReference
    private Processo processo;

    public DocumentoSolicitado(Documento documento, Processo novoProcesso) {
        this.obrigatorio = true;
        this.documento = documento;
        this.processo = novoProcesso;
        this.status = StatusDocumentoSolicitado.NAO_ENVIADO;
    }
}
