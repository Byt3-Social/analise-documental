package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DadoSolicitadoRepository extends JpaRepository<DadoSolicitado, Integer> {
    List<DadoSolicitado> findByProcesso(Processo processo);
}
