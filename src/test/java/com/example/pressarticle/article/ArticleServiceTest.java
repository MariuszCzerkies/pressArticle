package com.example.pressarticle.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @DisplayName("Should return all Articles")
    @Test
    void findAllArticleTransfer() {
        //give
        List<Article> list = new ArrayList<>();
        list.add(new Article(1L, "NewWorld", LocalDate.of(1022,9,17),
                        "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));
        list.add(new Article(2L, "NewWorld", LocalDate.of(1022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

        Mockito.when(articleRepository.findAll()).thenReturn(list);

        //when
        List<Article> result = articleService.findAllArticleTransfer();

        //then
        assertEquals(list.size(), result.size());
    }

    @DisplayName("Should return sort Articles")
    @Test
    void sortAllTransfer() {
        //given
        List<Article> list = new ArrayList<>();
        list.add(new Article(1L, "NewWorld", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));
        list.add(new Article(2L, "NewWorld", LocalDate.of(1022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

       // list = list.stream()
                //   .sorted(Comparator.comparing(Article::getDataPublication).reversed())
                  // .collect(Collectors.toList());

        list.sort(Comparator.comparing(Article::getDataPublication).reversed());

        Mockito.when(articleRepository.findArticleByQuery()).thenReturn(list);

        //when
        List<Article> articleList =  articleService.sortAllTransfer();

        //then
        assertEquals(list.get(0), articleList.get(0));
    }
    @DisplayName("Should return Articles for id")
    @Test
    void articleTransfer() {
        //given
        List<Article> article = new ArrayList<>();
        article.add(new Article(1L, "NewWorld", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

        Mockito.when(articleRepository.findArticleById(Mockito.anyLong())).thenReturn(article);

        //when
        List<Article> result= articleService.articleTransfer(1L);

        //then
        assertEquals(article.size(), result.size());
    }

    @Test
    void articleTitleTransfer() {
        //given
        List<Article> article = new ArrayList<>();
        article.add(new Article(1L, "NewWorld", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

       // Mockito.when(articleRepository.findArticleByDescribe(Mockito.anyString())).thenReturn(article);
        Mockito.when(articleRepository.findArticleByDescribe("NewWorld")).thenReturn(article);

        //when
        List<Article> result= articleService.articleTitleTransfer("NewWorld");
        //then
        assertEquals(article.size(), result.size());
    }

    @Test
    void addArticleTransfer() {
        //given
        Article article = new Article(1L, "NewWorld", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0));

        Mockito.when(articleRepository.save(article)).thenReturn(article);

        //when
        Article result = articleService.addArticleTransfer(article);

        //then
        assertEquals(article.getId(), result.getId());
    }

    @Test
    void updateArticleTransfer() {
        //given
        Article article = new Article(1L, "updateNewWorld", LocalDate.of(2022,9,17),
                "updateWorld", "Update Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0));

        Mockito.when(articleRepository.save(article)).thenReturn(article);

        //when
        Article result = articleService.addArticleTransfer(article);

        //then
        assertEquals(article.getDescribe(), result.getDescribe());
        assertEquals(article.getNameMagazine(), result.getNameMagazine());
    }

    @Test
    void deleteArticleTransfer() {
        //given
        List<Article> list = new ArrayList<>();
        list.add(new Article(1L, "NewWorld", LocalDate.of(2022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));
        list.add(new Article(2L, "NewWorld", LocalDate.of(1022,9,17),
                "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

        Mockito.when(articleRepository.delete(list.get(0).getId())).thenReturn(list);

        //when
            articleService.deleteArticleTransfer(1L);

        //then
        assertEquals(1, list.size());

    }
}