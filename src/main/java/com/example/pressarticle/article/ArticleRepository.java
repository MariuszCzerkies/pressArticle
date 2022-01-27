package com.example.pressarticle.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a order by a.dataPublication desc")
    List<Article> findArticleByQuery();

    List<Article> findArticleById(Long id);

    List<Article> findArticleByDescribe(String title);
}
