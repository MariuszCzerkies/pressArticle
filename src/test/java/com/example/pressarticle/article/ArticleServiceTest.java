package com.example.pressarticle.article;

import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.domain.repository.ArticleRepository;
import com.example.pressarticle.article.domain.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)//JUnit5
//@MockitoSettings(strictness = Strictness.LENIENT)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @Test
    @DisplayName("Should return sort Articles")
    void sortAllTransfer() {
        //given
        List<Article> list = new ArrayList<>();
        list.add(new Article(1L, "Machines replace human","Machines", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")));
        list.add(new Article(2L, "Robots are a system computer","Robots", LocalDate.of(1022,8,11),
                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")));

        list.sort(Comparator.comparing(Article::getDataPublication).reversed());

        Mockito
                .when(
                        articleRepository.findAll(Mockito.any(Pageable.class))
                )
                .thenReturn(new PageImpl<>(list));

        //when
        List<Article> articleList =  articleService
                .sortAllArticleTransfer(0, 20, "data_Publication");

        //then
        assertEquals(list.get(0), articleList.get(0));
    }

    @Test
    @DisplayName("Should return Articles for id")
    void articleIdTransfer() {
        //given
       Article article =
        new Article(1L, "NewWorld", "NewTitle",LocalDate.of(2022,9,17),
                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z"));

//        List<Article> article = new ArrayList<>();
//        article.add(new Article(1L, "NewWorld", "NewTitle",LocalDate.of(2022,9,17),
//                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")));

        //Mockito.when(articleRepository.findArticleById(Mockito.anyLong())).thenReturn(article);
        Mockito.when(articleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(article));

        //when
       Article result= articleService.findArticleById(1L);

        //then
       // assertEquals(article.size(), result.size());
         assertEquals(article.getId(), result.getId());
    }

    @Test
    @DisplayName("Should return Articles for text")
    void articleFindDescriptionTransfer() {
        //given
        List<Article> article = new ArrayList<>();
        article.add(new Article(1L, "NewWorld", "NewDescription", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")));

        Mockito
                .when(
                        articleRepository.findArticleByDescriptionOrTitle(
                                Mockito.anyString(), Mockito.anyString()
                        )
                )
                .thenReturn(article);

        //when
        List<Article> result = articleService.findArticlesByDescriptionOrTitle("NewWorld", "World");

        //then
        assertEquals(article.size(), result.size());
    }

    @Test
    @DisplayName("Should add Article")
    void addArticleTransfer() {
        //given
        Article article = new Article(1L, "NewWorld", "NewTitleWorld",LocalDate.of(2022,9,17),
                "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z"));

        Mockito.when(articleRepository.save(article)).thenReturn(article);

        //when
        Article result = articleService.addArticleTransfer(article);

        //then
        assertEquals(article.getId(), result.getId());
    }

    @Test
    @DisplayName("Should update Article")
    void updateArticleTransfer() {
        //given
        Article article = new Article(1L, "updateNewWorld","updateText", LocalDate.of(2022,9,17),
                "updateWorld", "Update Allan Balkier", Instant.parse("2018-08-22T10:00:00Z"));

        Mockito.when(articleRepository.save(article)).thenReturn(article);

        //when
        Article result = articleService.addArticleTransfer(article);

        //then
        assertEquals(article.getDescribeText(), result.getDescribeText());
        assertEquals(article.getNameMagazine(), result.getNameMagazine());
    }

    @Test
    @DisplayName("Should delete Article")
    void deleteArticleTransfer() {
        //given
        var SOME_ID = 1L;

        //when
        articleService.deleteArticleTransfer(SOME_ID);

        //then
        Mockito.verify(articleRepository).deleteById(SOME_ID);
    }
}