package com.digitaldesign.murashkina.models.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.digitaldesign.murashkina.models")
public class ModelsConfiguration {
}
