package com.example.converter.infra.config;

import com.example.converter.presentataion.converter.LocalDateConvereter;
import com.example.converter.presentataion.formmater.LocalDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConvereter());
        registry.addFormatter(new LocalDateTimeFormatter());
    }
}
