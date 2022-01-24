package com.example.pressarticle.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articleAll")
    List<Article> findAll() {
        return articleRepository.findAll();
    }

    @GetMapping("/sort")
    List<Article> sortAll() {
        return articleRepository.findArticleByQuery();
    }

    @GetMapping("/article/{id}")
    List<Article> article(@PathVariable Long id) {
        return articleRepository.findArticleById(id);
    }

}