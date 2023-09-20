package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
}
