package com.byt3social.analisedocumental.repositories;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.dto.EnderecoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.byt3social.analisedocumental.enums.StatusProcesso;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProcessoRepositoryTest {

    @Test
    public void testCriarProcesso() {

        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"));
        Processo processo = new Processo(organizacaoDTO);
    
        assertNotNull(processo);
        assertEquals(1, processo.getCadastroId());
        assertEquals("12345678901234", processo.getCnpj());
        assertEquals("Nome Empresarial", processo.getNomeEmpresarial());
        assertNotNull(processo.getResponsavel());
        assertEquals(StatusProcesso.ABERTO, processo.getStatus());
        assertNotNull(processo.getUuid());
    }

    @Test
    public void testAtualizarProcesso() {

        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"));
        Processo processo = new Processo(organizacaoDTO);
        ProcessoDTO updatedProcessoDTO = new ProcessoDTO(
            processo.getId(),  
            1, "UpdatedCnpj", new Date(), "UpdatedNomeEmpresarial",
            "UpdatedNomeFantasia", new EnderecoDTO(
                "UpdatedRua", "UpdatedNumero", "UpdatedBairro", "UpdatedComplemento", "UpdatedCidade", "UpdatedEstado"
            ),
            "UpdatedPorte", "updated@email.com", "9876543210", null, null, null, null, null, null, null, null, null
        );

        processo.atualizar(updatedProcessoDTO);

        assertEquals("UpdatedCnpj", processo.getCnpj());
        assertEquals("UpdatedNomeEmpresarial", processo.getNomeEmpresarial());
        assertEquals("UpdatedNomeFantasia", processo.getNomeFantasia());
        assertNotNull(processo.getEndereco());
        assertEquals("UpdatedRua", processo.getEndereco().getEndereco());
    }
    
    @Test
    public void testVincularDadosSolicitados() {

        Processo processo = new Processo(new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
            "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321")));
        List<DadoSolicitado> dadosSolicitados = new ArrayList<>();
        DadoSolicitado dadoSolicitado1 = new DadoSolicitado(new Dado(), processo);
        DadoSolicitado dadoSolicitado2 = new DadoSolicitado(new Dado(), processo);

        dadosSolicitados.add(dadoSolicitado1);
        dadosSolicitados.add(dadoSolicitado2);
        processo.vincularDadosSolicitados(dadosSolicitados);

        assertEquals(dadosSolicitados, processo.getDadosSolicitados());
    }

    @Test
    public void testVincularDocumentosSolicitados() {

        Processo processo = new Processo(new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
            "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321")));
        List<DocumentoSolicitado> documentosSolicitados = new ArrayList<>();
        DocumentoSolicitado documentoSolicitado1 = new DocumentoSolicitado(new Documento(), processo);
        DocumentoSolicitado documentoSolicitado2 = new DocumentoSolicitado(new Documento(), processo);

        documentosSolicitados.add(documentoSolicitado1);
        documentosSolicitados.add(documentoSolicitado2);
        processo.vincularDocumentosSolicitados(documentosSolicitados);

        assertEquals(documentosSolicitados, processo.getDocumentosSolicitados());
    }

}