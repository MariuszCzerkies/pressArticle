package com.example.pressarticle.article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class ArticleController {

    Date date = new Date();
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articleAll")
    public List<Article> findAllArticle() {
        return articleService.findAllArticleTransfer();
    }

    //endpoint zwracający wszystkie artykuły prasowe posortowane malejąco po dacie publikacji
    @GetMapping("/sort")
    public List<Article> sortAll() {
        return articleService.sortAllTransfer();
    }

    //endpoint zwracający pojedynczy artykuł prasowy po id
    @GetMapping("/article/{id}")
    public List<Article> article(@PathVariable Long id) {
        return articleService.articleTransfer(id);
    }

    //endpoint zwracający listę wszystkich artykułów prasowych po słowie kluczowym zawartym w tytule lub treści publikacji
    @GetMapping("/articleTitle/{title}")
    public List<Article> articleTitle(@PathVariable String title) {
        return articleService.articleTitleTransfer(title);
    }

    //endpoint pozwalający na zapis artykułu prasowego
    @GetMapping("/articleAdd")
    public Article addArticle() {
       return articleService.addArticleTransfer(new Article(11L,
                                        "Programing Language",
                                                LocalDate.now(),
                                    "ProgramingFuture",
                                    "Paul Martin",
                                                new Timestamp(date.getTime())));
    }

    //endpoint do aktualizacji istniejącego artykułu prasowego
    @GetMapping("/articleUpdate/{id}")
    public Article updateArticle(@PathVariable Long id) {
      return articleService.updateArticleTransfer(id);
    }

    //endpoint do usuwania wybranego artykułu prasowego
    @GetMapping("/articleDelete/{id}")
    public void deleteArticle(@PathVariable Long id) {
      articleService.deleteArticleTransfer(id);
    }
}