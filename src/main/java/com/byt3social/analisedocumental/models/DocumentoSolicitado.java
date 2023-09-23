package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DocumentoSolicitadoDTO;
import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @JsonProperty("assinatura_digital")
    private String assinaturaDigital;
    private Boolean obrigatorio;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "documento_id")
    private Documento documento;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "processo_id")
    @JsonBackReference
    private Processo processo;

    public DocumentoSolicitado(Documento documento, Processo novoProcesso) {
        this.obrigatorio = true;
        this.documento = documento;
        this.processo = novoProcesso;
        this.status = StatusDocumentoSolicitado.NAO_ENVIADO;
    }

    public DocumentoSolicitado(DocumentoSolicitadoDTO documentoSolicitadoDTO, Processo processo, List<Documento> listaDocumentos) {
        this.url = documentoSolicitadoDTO.url();
        this.status = documentoSolicitadoDTO.status();
        this.assinaturaDigital = documentoSolicitadoDTO.assinaturaDigital();
        this.obrigatorio = documentoSolicitadoDTO.obrigatorio();

        if(documentoSolicitadoDTO.documento().id() != null) {
            this.documento = listaDocumentos.stream().filter(documentoComplementar -> documentoComplementar.getId().equals(documentoSolicitadoDTO.documento().id())).findFirst().get();
        } else {
            this.documento = new Documento(documentoSolicitadoDTO.documento());
        }

        this.processo = processo;
    }

    public void atualizaDocumentoSolicitado(DocumentoSolicitadoDTO documentoSolicitadoDTO, Processo processo) {
        if(documentoSolicitadoDTO.url() != null) {
            this.url = documentoSolicitadoDTO.url();
        }

        if(documentoSolicitadoDTO.status() != null) {
            this.status = documentoSolicitadoDTO.status();
        }

        if(documentoSolicitadoDTO.assinaturaDigital() != null) {
            this.assinaturaDigital = documentoSolicitadoDTO.assinaturaDigital();
        }

        if(documentoSolicitadoDTO.obrigatorio() != null) {
            this.obrigatorio = documentoSolicitadoDTO.obrigatorio();
        }

        if(documentoSolicitadoDTO.documento() != null) {
            if(this.documento.getId().equals(documentoSolicitadoDTO.documento().id())) {
                this.documento.atualizaDocumento(documentoSolicitadoDTO.documento());
            } else {
                this.documento = new Documento(documentoSolicitadoDTO.documento());
            }
        }

        this.processo = processo;
    }
}
