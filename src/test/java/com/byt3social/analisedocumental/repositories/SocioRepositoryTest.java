package com.byt3social.analisedocumental.repositories;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.models.Socio;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import com.byt3social.analisedocumental.dto.SocioDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SocioRepositoryTest {

    @Test
    public void testCriarSocio() {

        Processo processo = new Processo(new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321")));

        SocioDTO socioDTO = new SocioDTO(null, "Socio Nome", "12345678901", "Qualificacao do Socio");
        Socio socio = new Socio(socioDTO, processo);

        assertNotNull(socio);
        assertEquals("Socio Nome", socio.getNome());
        assertEquals("12345678901", socio.getCpf());
        assertEquals("Qualificacao do Socio", socio.getQualificacao());
        assertEquals(processo, socio.getProcesso());
    }

    @Test
    public void testAtualizarSocio() {

        Processo processo = new Processo(new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321")));
        SocioDTO initialSocioDTO = new SocioDTO(null, "Socio Inicial", "12345678901", "Qualificacao Inicial");
        Socio socio = new Socio(initialSocioDTO, processo);
        SocioDTO updatedSocioDTO = new SocioDTO(null, "Socio Atualizado", "9876543210", "Qualificacao Atualizada");

        socio.atualizar(updatedSocioDTO, processo);

        assertEquals("Socio Atualizado", socio.getNome());
        assertEquals("9876543210", socio.getCpf());
        assertEquals("Qualificacao Atualizada", socio.getQualificacao());
        assertEquals(processo, socio.getProcesso());
    }

}