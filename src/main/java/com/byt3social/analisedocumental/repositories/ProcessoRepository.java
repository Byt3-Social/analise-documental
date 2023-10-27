package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Processo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Integer> {
    List<Processo> findByCadastroId(Integer cadastroId, Sort sort);
}
