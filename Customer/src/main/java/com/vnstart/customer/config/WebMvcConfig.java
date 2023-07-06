package com.vnstart.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebMvcConfig {

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        var template = new ClassLoaderTemplateResolver();
        template.setSuffix(".html");
        template.setTemplateMode(TemplateMode.HTML);
        template.setCharacterEncoding("UTF-8");
        template.setCheckExistence(true);
        return template;
    }

    }
