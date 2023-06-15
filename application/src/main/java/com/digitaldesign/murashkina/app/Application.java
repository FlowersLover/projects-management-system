package com.digitaldesign.murashkina.app;

import com.digitaldesign.murashkina.dto.config.DtoConfiguration;
import com.digitaldesign.murashkina.models.config.ModelsConfiguration;
import com.digitaldesign.murashkina.repositories.config.RepositoriesConfiguration;
import com.digitaldesign.murashkina.services.config.ServicesConfiguration;
import com.digitaldesign.murashkina.services.security.SecurityConfiguration;
import com.digitaldesign.murashkina.web.config.WebConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({RepositoriesConfiguration.class,
        DtoConfiguration.class,
        ModelsConfiguration.class,
        ServicesConfiguration.class,
        WebConfiguration.class,
        SecurityConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



}
