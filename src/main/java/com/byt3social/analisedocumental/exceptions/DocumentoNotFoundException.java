package com.byt3social.analisedocumental.exceptions;

public class DocumentoNotFoundException extends RuntimeException {
    public DocumentoNotFoundException() {
        super("Documento não encontrado no processo informado");
    }
}
