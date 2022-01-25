package com.example.pressarticle.article;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Spring - użyj cały kontekst Spring'a do testów
@SpringBootTest
//Podpowiedz Spring'owi, skąd wziąć / jak utworzyć MockMvc
@AutoConfigureMockMvc//do MockMvc
class ArticleControllerTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    //API do testowania endpointów (Web MVC)
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /articleAll -> HTTP 200, lista artykułów z bazy")
    void shouldFindAllArticle() throws Exception {
        //given
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articleAll"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        final var articlesFormDBInJSON = resoultActions.andReturn().getResponse().getContentAsString();
        List<Article> articlesFromDBAsJava = objectMapper.readValue(articlesFormDBInJSON, new TypeReference<>() {});

        //then
        assertEquals(6, articlesFromDBAsJava.size());
    }

    @Test
    void shouldSortAllArticle() throws Exception {
        //given
        List<Article> articleList = List.of (
                new Article(1L, "NewWorld", LocalDate.of(1022,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(2L, "Machines", LocalDate.of(2015,10,11), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(3L, "Robots", LocalDate.of(2014,8,21), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(4L, "Electrical", LocalDate.of(2012,7,8), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(5L, "NewWorld", LocalDate.of(2010,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(6L, "NewWorld", LocalDate.of(2011,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

        //when
        List<Article> exampleSortArticle = articleList.stream()
                .sorted(Comparator.comparing(Article::getDataPublication).reversed())
                .collect(Collectors.toList());
        List<Long> integerModelListId = new ArrayList<>();
            for (Article article : exampleSortArticle) {
                integerModelListId.add(article.getId());
                System.out.println(integerModelListId);
            }

        List<Long> compareListId = new ArrayList<>();
            for (int i = 0; i < exampleSortArticle.size(); i++) {
               compareListId.add(articleRepository.findArticleByQuery().get(i).getId());
            }

        //then
        assertEquals(integerModelListId, compareListId);
    }

    @Test
    void shouldFindArticleById() throws Exception {
        //given
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> idArticle = articleRepository.findArticleById(1L);

        //then
        assertEquals(1, idArticle.get(0).getId());
    }

    @Test
    void shouldGetAllArticlesTitle() throws Exception {
        //given
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articleTitle/NewWorld"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> describeArticle = articleRepository.findArticleByDescribe("NewWorld");

        //then
        assertEquals(3, describeArticle.size());
    }

    @Test
    void shouldAddArticle() throws Exception {
        //give
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articleAdd"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> addArticle = articleRepository.findAll();

        //then
        assertEquals(7, addArticle.size());
   }

    @Test
    void shouldUpdateArticle() throws Exception {
        //give
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articleUpdate/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> updateArticle = articleRepository.findArticleById(1L);

        //then
        assertEquals("Update", updateArticle.get(0).getDescribe());
        assertEquals("NewUpdate", updateArticle.get(0).getNameMagazine());
    }

    @Test
    void shouldDeleteArticle() throws Exception {
        //give
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articleDelete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> existArticle = articleRepository.findAll();

        //then
        assertEquals(5, existArticle.size());
    }
}