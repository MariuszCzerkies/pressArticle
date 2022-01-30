package com.example.pressarticle.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/article")
public class ArticleController {

    Date date = new Date();
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //endpoint zwracający wszystkie artykuły prasowe posortowane malejąco po dacie publikacji //paginacja
    @GetMapping("/articles")
    public ResponseEntity<Page<Article>> sortAllArticle(@RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                        @RequestParam(name = "size", required = false, defaultValue = "25") int pageSize) {
        return ResponseEntity.ok(articleService.sortAllArticleTransfer(pageNumber, pageSize));
    }

    //endpoint zwracający pojedynczy artykuł prasowy po id
    @GetMapping("/articleId/{id}")
//    public ResponseEntity<Article> getById(@PathVariable long id) {
//        Optional<Article> article = articleService.articleIdTransfer(id);
//        if (article.isPresent()) {
//            return new ResponseEntity<>(article.get(), HttpStatus.OK);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }
    public List<Article> articleId(@PathVariable Long id) {
        return articleService.articleIdTransfer(id);
    }

    //endpoint zwracający listę wszystkich artykułów prasowych po słowie kluczowym zawartym w tytule lub treści publikacji
    @GetMapping("/articleText")
    public List<Article> articleDescription(@RequestParam String text, @RequestParam String titleText) {
        return articleService.articleDescribeTransfer(text, titleText);
    }

    //endpoint pozwalający na zapis artykułu prasowego
    @PostMapping(path ="/articleAdd", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    //public ResponseEntity<Article> addArticle(@RequestBody Article article) {
    public ResponseEntity<Article> addArticle(@RequestBody Article newArticle) {

        Article article = articleService.addArticleTransfer(newArticle);
        return new ResponseEntity<>(article, CREATED);

//       return articleService.addArticleTransfer(new Article(11L,
//                                        "Programing Language","Programing",
//                                                LocalDate.now(),
//                                    "ProgramingFuture",
//                                    "Paul Martin",
//                                                new Timestamp(date.getTime())));
    }

    //endpoint do aktualizacji istniejącego artykułu prasowego
    @PutMapping(path = "/articleUpdate/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Article updateArticle(@PathVariable Long id) {
      return articleService.updateArticleTransfer(id);
    }

    //endpoint do usuwania wybranego artykułu prasowego
    @DeleteMapping(path = "/articleDelete/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteArticle(@PathVariable Long id) {
      articleService.deleteArticleTransfer(id);
    }
}