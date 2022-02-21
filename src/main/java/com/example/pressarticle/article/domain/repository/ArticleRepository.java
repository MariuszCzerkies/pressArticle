package com.example.pressarticle.article.domain.repository;

import com.example.pressarticle.article.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Page<Article> findAll(Pageable pageable);

    Article findById(Long id);
    //Optional<Article> findById(Long id);

    //List<Article> findArticleByDescribe(String text, String titleText);

    Article save(Article entity);

    void deleteById(Long id);
}
