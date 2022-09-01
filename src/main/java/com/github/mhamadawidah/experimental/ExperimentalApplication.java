package com.github.mhamadawidah.experimental;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API", version = "1.0.0"))
public class ExperimentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExperimentalApplication.class, args);
    }
}
