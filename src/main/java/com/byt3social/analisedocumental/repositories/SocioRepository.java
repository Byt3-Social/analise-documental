package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.models.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Integer> {
    List<Socio> findByProcesso(Processo processo);
}
