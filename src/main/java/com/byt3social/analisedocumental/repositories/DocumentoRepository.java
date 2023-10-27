package com.byt3social.analisedocumental.repositories;

import com.byt3social.analisedocumental.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    List<Documento> findDocumentoByPadrao(Boolean padrao);
}
