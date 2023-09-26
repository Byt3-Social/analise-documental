ALTER TABLE processos RENAME COLUMN data_criacao TO created_at;

ALTER TABLE processos RENAME COLUMN data_atualizacao TO updated_at;

ALTER TABLE processos ADD COLUMN cadastro_id INT NOT NULL;

ALTER TABLE processos RENAME COLUMN link TO uuid;

ALTER TABLE documentos_solicitados RENAME COLUMN url TO caminho_s3;

ALTER TABLE documentos_solicitados ADD COLUMN nome_arquivo_original VARCHAR(255) NULL;

ALTER TABLE documentos_solicitados ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE documentos_solicitados ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE documentos_solicitados ADD COLUMN tamanho_arquivo BIGINT NULL;