package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DadoSolicitadoRepositoryTest {
    
    @Test
    public void testCriarDadoSolicitado() {

        DadoDTO dadoDTO = new DadoDTO("Nome Dado", "Tipo Dado", true);
        Dado dado = new Dado(dadoDTO);
        Processo processo = new Processo();
        DadoSolicitado dadoSolicitado = new DadoSolicitado(dado, processo);

        assertEquals("Nome Dado", dadoSolicitado.getDado().getNome());
        assertTrue(dadoSolicitado.getObrigatorio());
        assertEquals(dado, dadoSolicitado.getDado());
        assertEquals(processo, dadoSolicitado.getProcesso());
    }

    @Test
    public void testAtualizarDadoSolicitado() {

        DadoDTO dadoDTO = new DadoDTO("Nome Dado", "Tipo Dado", true);
        Dado dado = new Dado(dadoDTO);
        Processo processo = new Processo();
        DadoSolicitado dadoSolicitado = new DadoSolicitado(dado, processo);
        DadoSolicitadoDTO dadoSolicitadoDTO = new DadoSolicitadoDTO(null, "Valor Atualizado");
        dadoSolicitado.atualizar(dadoSolicitadoDTO);

        assertEquals("Valor Atualizado", dadoSolicitado.getValor());
        assertFalse(dadoSolicitado.getObrigatorio());
        assertEquals(dado, dadoSolicitado.getDado());
        assertEquals(processo, dadoSolicitado.getProcesso());
    }
}