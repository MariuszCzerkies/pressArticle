package com.example.pressarticle.article.controller;

import com.example.pressarticle.article.service.ArticleService;
import com.example.pressarticle.article.model.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<Article>> sortAllArticle(
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "25") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "dataPublication") String sortBy
    ) {
        return ResponseEntity.ok(
                articleService.sortAllArticleTransfer(pageNumber, pageSize, sortBy)
        );
    }

    @GetMapping("/{id}")
    public List<Article> articleId(@PathVariable Long id) {
        return articleService.articleIdTransfer(id);
    }

    @GetMapping("/articleText")
    public List<Article> articleDescription(
            @RequestParam String text,
            @RequestParam String titleText
    ) {
        return articleService.articleDescribeTransfer(text, titleText);
    }

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Article> addArticle(@RequestBody Article newArticle) {
        Article article = articleService.addArticleTransfer(newArticle);
        return ResponseEntity
                .status(201)
                .body(article);
    }

    @PutMapping(
            path = "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody Article articleToUpdate
    ) {
        return ResponseEntity.ok(articleService.updateArticleTransfer(id, articleToUpdate));
    }

    @DeleteMapping(
            path = "/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity deleteArticle(
            @PathVariable Long id
    ) {
        articleService.deleteArticleTransfer(id);
        return ResponseEntity
                .accepted()
                .build();
    }
}