package com.digitaldesign.murashkina.repositories.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.digitaldesign.murashkina.repositories")
@ComponentScan("com.digitaldesign.murashkina.repositories")
public class RepositoriesConfiguration {
}
