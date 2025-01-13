package com.b_cube.website.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "B-cube API Specifications", description = "비큐브 웹사이트 API 명세서", version = "1.0"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
}