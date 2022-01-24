package com.example.pressarticle.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class ArticleController {

    Date date = new Date();

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articleAll")
    List<Article> findAll() {
        return articleRepository.findAll();
    }

    //endpoint zwracający wszystkie artykuły prasowe posortowane malejąco po dacie publikacji
    @GetMapping("/sort")
    List<Article> sortAll() {
        return articleRepository.findArticleByQuery();
    }

    //endpoint zwracający pojedynczy artykuł prasowy po id
    @GetMapping("/article/{id}")
    List<Article> article(@PathVariable Long id) {
        return articleRepository.findArticleById(id);
    }

    //endpoint zwracający listę wszystkich artykułów prasowych po słowie kluczowym zawartym w tytule lub treści publikacji
    @GetMapping("/articleTitle/{title}")
    List<Article> articleTitle(@PathVariable String title) {
        return articleRepository.findArticleByDescribe(title);
    }

    //endpoint pozwalający na zapis artykułu prasowego
    @GetMapping("/articleAdd")
    void addArticle() {

        Article article = new Article(7L, "Programing Language", LocalDate.now(), "ProgramingFuture", "Paul Martin", new Timestamp(date.getTime()));
        articleRepository.save(article);
    }

    //endpoint do aktualizacji istniejącego artykułu prasowego
    @GetMapping("/articleUpdate/{id}")
    void updateArticle(@PathVariable Long id) {
        Article article = new Article(id, "Update", LocalDate.now(), "NewUpdate", "New Author", new Timestamp(date.getTime()));
        articleRepository.save(article);
    }

    //endpoint do usuwania wybranego artykułu prasowego
    @GetMapping("/articleDelete/{id}")
    void deleteArticle(@PathVariable Long id) {
        Article article = new Article(id);
        articleRepository.delete(article);
    }
}