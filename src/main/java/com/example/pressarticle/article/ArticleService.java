package com.example.pressarticle.article;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    Date date = new Date();
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAllArticleTransfer() {
      //  var result = articleRepository.findAll();
        //var filtered = Collections.singletonList(result.get(0));
       // return filtered;
        return articleRepository.findAll();
    }

    public List<Article> sortAllTransfer() {
        return articleRepository.findArticleByQuery();
    }

    public List<Article> articleTransfer(@PathVariable Long id) {
        return articleRepository.findArticleById(id);
    }

    public List<Article> articleTitleTransfer(@PathVariable String title) {
        return articleRepository.findArticleByDescribe(title);
    }

    public Article addArticleTransfer(Article article) {
       return articleRepository.save(article);
    }

    public Article updateArticleTransfer(@PathVariable Long id) {
       Article article = new Article(id, "Update", LocalDate.now(), "NewUpdate", "New Author", new Timestamp(date.getTime()));
       return articleRepository.save(article);
    }

    public void deleteArticleTransfer(@PathVariable Long id) {
        //Article article = new Article(id);
        articleRepository.deleteById(id);
    }
}
