package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Dado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadoRepository extends JpaRepository<Dado, Integer> {
}
