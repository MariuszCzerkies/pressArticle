package com.example.pressarticle.article.config;

import com.example.pressarticle.article.controller.dto.ArticleMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")//
public class DomainConfiguration {

//    @Bean
//    public ArticleRepository articleRepository(ArticleMapper mapper) {
//        return new ArticleStorageAdapter(mapper);
//    }

    @Bean
    public ArticleMapper articleMapper() {
        return Mappers.getMapper(ArticleMapper.class);
    }
}
