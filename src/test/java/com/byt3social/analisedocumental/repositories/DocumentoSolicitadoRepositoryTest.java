package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.enums.StatusDocumentoSolicitado;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DocumentoSolicitadoRepositoryTest {

    @Test
    public void testCriarDocumentoSolicitado() {

        Documento documento = new Documento(new DocumentoDTO("Nome Documento", true));
        Processo processo = new Processo();

        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        assertNotNull(documentoSolicitado);
        assertTrue(documentoSolicitado.getObrigatorio());
        assertEquals(StatusDocumentoSolicitado.NAO_ENVIADO, documentoSolicitado.getStatus());
    }

    @Test
    public void testAtualizarDocumentoSolicitado() {

        Documento documento = new Documento(new DocumentoDTO("Nome Documento", true));
        Processo processo = new Processo();
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        documentoSolicitado.atualizar("pathDocumento", "nomeArquivoOriginal", BigInteger.valueOf(1000));

        assertEquals("pathDocumento", documentoSolicitado.getCaminhoS3());
        assertEquals("nomeArquivoOriginal", documentoSolicitado.getNomeArquivoOriginal());
        assertEquals(BigInteger.valueOf(1000), documentoSolicitado.getTamanhoArquivo());
        assertEquals(StatusDocumentoSolicitado.ENVIADO, documentoSolicitado.getStatus());
        assertNotNull(documentoSolicitado.getUpdatedAt());
    }

    @Test
    public void testRemoverEnvioDocumentoSolicitado() {

        Documento documento = new Documento(new DocumentoDTO("Nome Documento", true));
        Processo processo = new Processo();
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        documentoSolicitado.atualizar("pathDocumento", "nomeArquivoOriginal", BigInteger.valueOf(1000));
        documentoSolicitado.removerEnvio();

        assertNull(documentoSolicitado.getCaminhoS3());
        assertNull(documentoSolicitado.getNomeArquivoOriginal());
        assertNull(documentoSolicitado.getTamanhoArquivo());
        assertEquals(StatusDocumentoSolicitado.NAO_ENVIADO, documentoSolicitado.getStatus());
        assertNotNull(documentoSolicitado.getUpdatedAt());
    }
    
    @Test
    public void testSolicitarReenvioDocumentoSolicitado() {

        Documento documento = new Documento(new DocumentoDTO("Nome Documento", true));
        Processo processo = new Processo();
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documento, processo);

        documentoSolicitado.solicitarReenvio();

        assertEquals(StatusDocumentoSolicitado.PENDENTE_REENVIO, documentoSolicitado.getStatus());
    }

}