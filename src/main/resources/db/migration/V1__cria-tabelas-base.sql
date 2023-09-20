CREATE TABLE `processos`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cnpj` VARCHAR(255) UNIQUE NOT NULL,
    `data_abertura` DATE NULL,
    `nome_empresarial` VARCHAR(255) NULL,
    `nome_fantasia` VARCHAR(255) NULL,
    `porte` VARCHAR(255) NULL,
    `endereco` VARCHAR(255) NULL,
    `numero` VARCHAR(255) NULL,
    `bairro` VARCHAR(255) NULL,
    `complemento` VARCHAR(255) NULL,
    `cidade` VARCHAR(255) NULL,
    `estado` VARCHAR(255) NULL,
    `email` VARCHAR(255) NULL,
    `telefone` VARCHAR(255) NULL,
    `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `data_atualizacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status` VARCHAR(255) NULL,
    `nome_responsavel` VARCHAR(255) NULL,
    `email_responsavel` VARCHAR(255) NULL,
    `telefone_responsavel` VARCHAR(255) NULL,
    `link` VARCHAR(255) NULL,
    `feedback` TEXT NULL
);

CREATE TABLE `socios`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL,
    `cpf` VARCHAR(255) NULL,
    `qualificacao` VARCHAR(255) NULL,
    `processo_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY(`processo_id`) REFERENCES `processos`(`id`)
);

CREATE TABLE `tipos_dados_complementares`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL,
    `tipo` VARCHAR(255) NULL
);

CREATE TABLE `dados_complementares`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `valor` TEXT NULL,
    `processo_id` INT UNSIGNED NOT NULL,
    `tipo_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY(`processo_id`) REFERENCES `processos`(`id`),
    FOREIGN KEY(`tipo_id`) REFERENCES `tipos_dados_complementares`(`id`)
);

CREATE TABLE `tipos_documentos`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL
);

CREATE TABLE `documentos`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `url` VARCHAR(255) NULL,
    `tipo_documento_id` INT UNSIGNED NOT NULL,
    `processo_id` INT UNSIGNED NOT NULL,
    `assinatura_digital` VARCHAR(255) NULL,
    `status` VARCHAR(255) NULL,
    FOREIGN KEY(`tipo_documento_id`) REFERENCES `tipos_documentos`(`id`),
    FOREIGN KEY(`processo_id`) REFERENCES `processos`(`id`)
);