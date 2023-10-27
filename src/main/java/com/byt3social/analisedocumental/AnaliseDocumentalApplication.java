package com.byt3social.analisedocumental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "API Análise Documental",
        version = "1.0",
        description = "API destinada ao gerenciamento e análise de documentos.",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "Byt3Social",
            email = "byt3social@gmail.com"
        )
    )
)
@SpringBootApplication
public class AnaliseDocumentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnaliseDocumentalApplication.class, args);
	}

}
