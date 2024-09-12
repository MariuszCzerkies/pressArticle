package com.example.pressarticle.article.external.repository;

import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.domain.repository.ArticleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaArticleRepository extends JpaRepository<Article, Long>, ArticleRepository {
    @Query(nativeQuery = true, value = "SELECT * FROM article WHERE describe_Text LIKE %?1% OR title_Text LIKE %?2%")
    List<Article> findArticleByDescriptionOrTitle(String description, String title);
}
