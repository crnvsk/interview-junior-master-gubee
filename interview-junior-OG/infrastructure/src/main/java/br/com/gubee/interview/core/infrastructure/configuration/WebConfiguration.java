package br.com.gubee.interview.core.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }
}