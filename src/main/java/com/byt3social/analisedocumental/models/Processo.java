package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Processo")
@Table(name = "processos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    @Column(name = "data_abertura")
    private String dataAbertura;
    @Column(name = "nome_empresarial")
    private String nomeEmpresarial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    private String porte;
    @Embedded
    private Endereco endereco;
    private String email;
    private String telefone;
    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private Timestamp dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private Timestamp dataAtualizacao;
    @Enumerated(value = EnumType.STRING)
    private StatusProcesso status;
    @Embedded
    private Responsavel responsavel;
    private String link;
    private String feedback;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "processo_id", referencedColumnName = "id")
    List<Socio> socios = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "processo_id", referencedColumnName = "id")
    private List<DocumentoSolicitado> documentosSolicitados = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "processo_id", referencedColumnName = "id")
    List<DadoSolicitado> dadosSolicitados = new ArrayList<>();

    public Processo(String cnpj) {
        this.cnpj = cnpj;
        this.status = StatusProcesso.CRIADO;
    }
}
