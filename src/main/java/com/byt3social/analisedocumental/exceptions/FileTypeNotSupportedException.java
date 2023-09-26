package com.byt3social.analisedocumental.exceptions;

public class FileTypeNotSupportedException extends RuntimeException {
    public FileTypeNotSupportedException() {
        super("Tipo de arquivo não suportado");
    }
}
