package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.repositories.DocumentoSolicitadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class ComplianceService {
    @Autowired
    private S3Client s3Client;
    @Autowired
    private DocumentoSolicitadoRepository documentoSolicitadoRepository;
    @Autowired
    private AmazonS3Service amazonS3Service;

    public String baixarDocumentoSolicitado(Integer documentoId) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoId).get();
        String nomeArquivo = documentoSolicitado.getUrl();

        return amazonS3Service.recuperarArquivo(nomeArquivo);
    }

    public void excluirDocumentoSolicitado(Integer documentoId) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoId).get();
        String pathArquivo = documentoSolicitado.getUrl();

        amazonS3Service.excluirArquivo(pathArquivo);
    }
}
