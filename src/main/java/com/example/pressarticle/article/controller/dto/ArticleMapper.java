package com.example.pressarticle.article.controller.dto;

import com.example.pressarticle.article.domain.model.Article;
import org.mapstruct.Mapper;

@Mapper
public interface ArticleMapper {

    ArticleDto toDto(Article article);

    Article toDomain(ArticleDto dto);
}
