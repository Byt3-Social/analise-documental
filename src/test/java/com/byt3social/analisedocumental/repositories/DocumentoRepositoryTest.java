package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DocumentoRepositoryTest {
    
    @Test
    public void testCriarDocumento() {

        DocumentoDTO documentoDTO = new DocumentoDTO("Nome Documento", true);
        Documento documento = new Documento(documentoDTO);

        assertEquals("Nome Documento", documento.getNome());
        assertTrue(documento.getPadrao());
    }

    @Test
    public void testAtualizarDocumento() {

        DocumentoDTO documentoDTO = new DocumentoDTO("Nome Documento", true);
        Documento documento = new Documento(documentoDTO);
        DocumentoDTO updatedDocumentoDTO = new DocumentoDTO("Nome Atualizado", false);

        documento.atualizar(updatedDocumentoDTO);

        assertEquals("Nome Atualizado", documento.getNome());
        assertFalse(documento.getPadrao());
    }
}