package com.example.pressarticle.article.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ArticleResponse {
   private List<ArticleDto> articles;

   public ArticleResponse(List<ArticleDto> articles) {
      this.articles = articles;
   }
}
