package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Service Api",
                description = "Task Service", version = "1.0.0",
                contact = @Contact(
                        name = "Ilya",
                        url = "https://github.com/ism-ektb"
                )
        )
)
public class OpenApiConfig {

}