package com.example.pressarticle.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    Date date = new Date();
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<Article> sortAllArticleTransfer(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
       // Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Article> quotations = articleRepository.findAll(pageable);
        quotations.stream()
                .sorted(Comparator.comparing(Article::getDataPublication).reversed())
                .collect(Collectors.toList());
        return quotations;
        
        
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Sort sortByDataPublication = Sort.by("dataPublication").descending();
//        return articleRepository.findAll(sortByDataPublication);
    }

    public List<Article> articleIdTransfer(@PathVariable Long id) {
        return articleRepository.findArticleById(id);
    }

    public List<Article> articleDescribeTransfer(@RequestParam String text, @RequestParam String titleText) {
        return articleRepository.findArticleByDescribe(text, titleText);
    }

    public Article addArticleTransfer(Article article) {
//        article = new Article(11L,"Programing Language","Programing", LocalDate.now(), "ProgramingFuture",
//                "Paul Martin", new Timestamp(date.getTime()));
       return articleRepository.save(article);
    }

    public Article updateArticleTransfer(@PathVariable Long id) {
       Article article = new Article(id, "Update","UpdateText", LocalDate.now(), "NewUpdate", "New Author", new Timestamp(date.getTime()));
       return articleRepository.save(article);
    }

    public void deleteArticleTransfer(@PathVariable Long id) {
        articleRepository.deleteById(id);
    }
}
