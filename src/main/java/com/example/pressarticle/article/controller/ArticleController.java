package com.example.pressarticle.article.controller;

import com.example.pressarticle.article.controller.dto.ArticleDto;
import com.example.pressarticle.article.controller.dto.ArticleMapper;
import com.example.pressarticle.article.controller.dto.ArticleResponse;
import com.example.pressarticle.article.domain.ArticleService;
import com.example.pressarticle.article.domain.model.Article;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;
    private ArticleMapper articleMapper;

    public ArticleController(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleDto>> sortAllArticle(
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "25") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "dataPublication") String sortBy
    ) {
        List<ArticleDto> articleDtoList = articleService.sortAllArticleTransfer(pageNumber, pageSize, sortBy).stream()
                .map(article -> articleMapper.toDto(article))
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        Article article = articleService.findArticleById(id);
        return ResponseEntity.ok(articleMapper.toDto(article));
    }

    @GetMapping("/search")
    public ResponseEntity<ArticleResponse> searchForArticles(
            @RequestParam String description,
            @RequestParam String title
    ) {
        List<ArticleDto> list = articleService.findArticlesByDescriptionOrTitle(description, title).stream()
                .map(article -> articleMapper.toDto(article))
                .collect(Collectors.toList());
        ArticleResponse articleResponse = new ArticleResponse(list);
        return ResponseEntity.ok(articleResponse);
    }

    @PostMapping()
    public ResponseEntity addArticle(@RequestBody Article newArticle, UriComponentsBuilder uriComponentsBuilder) {
        Article article = articleService.addArticleTransfer(newArticle);

        var url = uriComponentsBuilder.path("/articles/{id}").buildAndExpand(article.getId()).toUri();
        return ResponseEntity.created(url).build();

        //HttpHeaders headers = new HttpHeaders();//nagłówki
        //headers.setLocation(uriComponentsBuilder.path("/articles/{id}").buildAndExpand(article.getId()).toUri());
        //return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }

    @PutMapping(
            path = "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleDto> updateArticle(
            @PathVariable Long id,
            @RequestBody Article articleToUpdate
    ) {
        Article article = articleService.updateArticleTransfer(id, articleToUpdate);
        return ResponseEntity.ok(articleMapper.toDto(article));
        //return ResponseEntity.ok(articleService.updateArticleTransfer(id, articleToUpdate));
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