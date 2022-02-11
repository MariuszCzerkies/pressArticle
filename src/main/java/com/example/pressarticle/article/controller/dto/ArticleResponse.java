package com.example.pressarticle.article.controller.dto;

import java.util.List;

public class ArticleResponse {
   private List<ArticleDto> articles;

   public ArticleResponse(List<ArticleDto> articles) {
      this.articles = articles;
   }
}
