package com.example.pressarticle.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articleAll")
    List<Article> findAllArticle() {
        return articleService.findAllArticleTransfer();
    }

    //endpoint zwracający wszystkie artykuły prasowe posortowane malejąco po dacie publikacji
    @GetMapping("/sort")
    List<Article> sortAll() {
        return articleService.sortAllTransfer();
    }

    //endpoint zwracający pojedynczy artykuł prasowy po id
    @GetMapping("/article/{id}")
    List<Article> article(@PathVariable Long id) {
        return articleService.articleTransfer(id);
    }

    //endpoint zwracający listę wszystkich artykułów prasowych po słowie kluczowym zawartym w tytule lub treści publikacji
    @GetMapping("/articleTitle/{title}")
    List<Article> articleTitle(@PathVariable String title) {
        return articleService.articleTitleTransfer(title);
    }

    //endpoint pozwalający na zapis artykułu prasowego
    @GetMapping("/articleAdd")
    void addArticle() {
        articleService.addArticleTransfer();
    }

    //endpoint do aktualizacji istniejącego artykułu prasowego
    @GetMapping("/articleUpdate/{id}")
    void updateArticle(@PathVariable Long id) {
       articleService.updateArticleTransfer(id);
    }

    //endpoint do usuwania wybranego artykułu prasowego
    @GetMapping("/articleDelete/{id}")
    void deleteArticle(@PathVariable Long id) {
      articleService.deleteArticleTransfer(id);
    }
}