package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

@Entity(name = "DocumentoSolicitado")
@Table(name = "documentos_solicitados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class DocumentoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "caminho_s3")
    @JsonProperty("caminho_s3")
    private String caminhoS3;
    @Column(name = "nome_arquivo_original")
    @JsonProperty("nome_arquivo_original")
    private String nomeArquivoOriginal;
    @Column(name = "tamanho_arquivo")
    @JsonProperty("tamanho_arquivo")
    private BigInteger tamanhoArquivo;
    @Enumerated(value = EnumType.STRING)
    private StatusDocumentoSolicitado status;
    @JsonProperty("assinatura_digital")
    private String assinaturaDigital;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
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

    public void atualizar(String pathDocumento, String nomeArquivoOriginal, BigInteger tamanhoArquivo) {
        this.caminhoS3 = pathDocumento;
        this.nomeArquivoOriginal = nomeArquivoOriginal;
        this.tamanhoArquivo = tamanhoArquivo;
        this.status = StatusDocumentoSolicitado.ENVIADO;
        this.updatedAt = Date.from(Instant.now());
    }

    public void removerEnvio() {
        this.caminhoS3 = null;
        this.nomeArquivoOriginal = null;
        this.tamanhoArquivo = null;
        this.status = StatusDocumentoSolicitado.NAO_ENVIADO;
        this.updatedAt = Date.from(Instant.now());
    }

    public void solicitarReenvio() {
        this.status = StatusDocumentoSolicitado.PENDENTE_REENVIO;
    }
}
