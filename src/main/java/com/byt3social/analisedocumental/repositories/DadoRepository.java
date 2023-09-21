package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Dado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DadoRepository extends JpaRepository<Dado, Integer> {
    List<Dado> findDadoByPadrao(Boolean padrao);
}
