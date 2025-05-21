package br.com.gubee.interview.core.infrastructure.configuration;

import br.com.gubee.interview.core.domain.utils.mappers.HeroMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public HeroMapper heroMapper() {
        return Mappers.getMapper(HeroMapper.class);
    }
}