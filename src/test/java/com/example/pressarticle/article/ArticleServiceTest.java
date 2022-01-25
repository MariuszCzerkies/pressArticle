package com.example.pressarticle.article;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;

class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void findAllArticleTransfer() {
        //give

        //when

        //then


    }

    @Test
    void sortAllTransfer() {
    }

    @Test
    void articleTransfer() {
    }

    @Test
    void articleTitleTransfer() {
    }

    @Test
    void addArticleTransfer() {
        //given
        Article exampleArticle = new Article(
                14L, "Programing Language Java",
                LocalDate.of(2021,1,23),
                "ProgramingFuture",
                "Paul Martin",
                new Timestamp(2007, 11, 23, 0, 0, 0, 0));
        System.out.println("--------------> " + exampleArticle);
        articleService.addArticleTransfer(exampleArticle);

        //when
        //Article readArticle = articleService.articleTransfer(exampleArticle.getId());

        //then
        //assertEquals(exampleArticle.getId(), readArticle.getId());
    }

    @Test
    void updateArticleTransfer() {
    }

    @Test
    void deleteArticleTransfer() {
    }
}