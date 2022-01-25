package com.example.pressarticle.article;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    Date date = new Date();
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    List<Article> findAllArticleTransfer() {
        return articleRepository.findAll();
    }

    List<Article> sortAllTransfer() {
        return articleRepository.findArticleByQuery();
    }

    List<Article> articleTransfer(@PathVariable Long id) {
        return articleRepository.findArticleById(id);
    }

    List<Article> articleTitleTransfer(@PathVariable String title) {
        return articleRepository.findArticleByDescribe(title);
    }

    void addArticleTransfer() {
        Article article = new Article(7L, "Programing Language", LocalDate.now(), "ProgramingFuture", "Paul Martin", new Timestamp(date.getTime()));
        articleRepository.save(article);
    }

    void updateArticleTransfer(@PathVariable Long id) {
        Article article = new Article(id, "Update", LocalDate.now(), "NewUpdate", "New Author", new Timestamp(date.getTime()));
        articleRepository.save(article);
    }

    void deleteArticleTransfer(@PathVariable Long id) {
        Article article = new Article(id);
        articleRepository.delete(article);
    }
}
