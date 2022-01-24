package com.example.pressarticle.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //endpoint zwracający wszystkie artykuły prasowe posortowane malejąco po dacie publikacji
    @Query("select a from Article a order by a.dataPublication desc")
    List<Article> findArticleByQuery();

    //endpoint zwracający pojedynczy artykuł prasowy po id
    List<Article> findArticleById(Long id);

    //endpoint zwracający listę wszystkich artykułów prasowych po słowie kluczowym zawartym w tytule lub treści publikacji
    List<Article> findArticleByDescribe(String title);
}
