package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DadoRepositoryTest {

    @Test
    public void testCriarDado() {
        DadoDTO dadoDTO = new DadoDTO("nome do dado", "tipo do dado", true);
        Dado dado = new Dado(dadoDTO);

        assertEquals("nome do dado", dado.getNome());
        assertEquals("tipo do dado", dado.getTipo());
        assertTrue(dado.getPadrao());
    }
    @Test
    public void testAtualizarDado() {

        DadoDTO initialDadoDTO = new DadoDTO("Nome Inicial", "Tipo Inicial", true);
        Dado dado = new Dado(initialDadoDTO);
        DadoDTO updatedDadoDTO = new DadoDTO("Nome Atualizado", "Tipo Atualizado", false);

        dado.atualizar(updatedDadoDTO);

        assertEquals("Nome Atualizado", dado.getNome());
        assertEquals("Tipo Atualizado", dado.getTipo());
        assertFalse(dado.getPadrao());
    }
}