package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.exceptions.FileTypeNotSupportedException;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.repositories.DocumentoSolicitadoRepository;
import com.byt3social.analisedocumental.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class OrganizacaoService {
    @Autowired
    private S3Client s3Client;
    @Autowired
    private ProcessoRepository processoRepository;
    @Autowired
    private AmazonS3Service amazonS3Service;
    @Autowired
    private DocumentoSolicitadoRepository documentoSolicitadoRepository;
    @Value("${com.byt3social.aws.main-bucket-name}")
    private String nomeBucketPrincipal;

    @Transactional
    public void enviarDocumentoSolicitado(Integer documentoSolicitadoId, MultipartFile documento) {
        DocumentoSolicitado documentoSolicitado = documentoSolicitadoRepository.findById(documentoSolicitadoId).get();
        Processo processo = documentoSolicitado.getProcesso();

        if(!FilenameUtils.isExtension(documento.getOriginalFilename(), "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        String pastaProcesso = processo.getId() + "_" + processo.getCnpj() + "/";
        String extensaoArquivo = FilenameUtils.getExtension(documento.getOriginalFilename());
        String nomeDocumento = processo.getCnpj() + " - " + documentoSolicitado.getDocumento().getNome() + "." +  extensaoArquivo;
        String pathArquivo = pastaProcesso + nomeDocumento;

        if(!amazonS3Service.existeObjeto(pastaProcesso)) {
            amazonS3Service.criarPasta(pastaProcesso);
        }

        amazonS3Service.armazenarArquivo(documento, pathArquivo);

        documentoSolicitado.atualizarInformacoes(pathArquivo);
    }
}
