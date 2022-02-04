package com.example.pressarticle.article;

import com.example.pressarticle.article.model.Article;
import com.example.pressarticle.article.repository.ArticleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional//w testach po każdym teście wycofuje zmiany zrobione na bazie ktore odbyly sie w trakcie wykonywania tego testu
class ArticleControllerTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET - get Sorted All Articles -> HTTP 200, sorted list articles from base")
    void shouldSortAllArticle() throws Exception {
        //given
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles"))
                .andDo(print())
                .andExpect(status().isOk());

        final var articleFormDBInJSON = resultActions.andReturn().getResponse().getContentAsString();
        List<Article> articleFromDBAsJava = objectMapper.readValue(articleFormDBInJSON, new TypeReference<>() {});

        List<Article> articleList = List.of (
                new Article(1L, "New World is for you","NewWorld", LocalDate.of(1022,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(2L, "Machines replace human","Machines", LocalDate.of(2015,10,11), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(3L, "Robots are a system computer","Robots", LocalDate.of(2014,8,21), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(4L, "Electrical 230VAC 24VDC","Electrical", LocalDate.of(2012,7,8), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(5L, "New World is for you","NewWorld", LocalDate.of(2010,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)),
                new Article(6L, "New World is for you","NewWorld", LocalDate.of(2011,9,17), "World", "Allan Balkier", new Timestamp(100, 10, 11, 0, 0, 0, 0)));

        //when
        articleList = articleList.stream()
                .sorted(Comparator.comparing(Article::getDataPublication).reversed())
                .collect(Collectors.toList());
        List<Long> exampleSortArticleId = new ArrayList<>();
        for (Article article : articleList) {
            exampleSortArticleId.add(article.getId());
        }

        List<Article> expectedSortId = new ArrayList<>();
        for (int i = 0; i < articleFromDBAsJava.size(); i++) {
            expectedSortId.add(articleFromDBAsJava.get(i));
        }

        expectedSortId.stream()
                .sorted(Comparator.comparing(Article::getDataPublication).reversed())
                .collect(Collectors.toList());

        List<Long> expectedSortedListId = new ArrayList<>();
        for (Article article : expectedSortId) {
            expectedSortedListId.add(article.getId());
        }

        //then
        assertEquals(exampleSortArticleId, expectedSortedListId);
    }

    @Test
    @DisplayName("GET - get Article By Id -> HTTP 201")
    void shouldFindArticleById() throws Exception {
        //given
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> idArticle = articleRepository.findArticleById(1L);

        //then
        assertEquals(1, idArticle.get(0).getId());
    }

    @Test
    @DisplayName("GET - get All Article By Description Or Title -> HTTP 200")
    void shouldGetAllArticlesDescription() throws Exception {
        //given
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles/articleText?text=New World&titleText=World"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> describeArticle = articleRepository.findArticleByDescribe("New World", "World");

        //then
        assertEquals(3, describeArticle.size());
    }

    @Test
    @DisplayName("POST - add All Article By Description Or Title -> HTTP 201")
    void shouldAddArticle() throws Exception {
        //give
        Article newArticle = new Article(11L,"Programing Language","Programing", LocalDateTime.now().toLocalDate(), "ProgramingFuture",
                "Paul Martin", new Timestamp(100, 10, 11, 0, 0, 0, 0));

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson= objectMapper.writeValueAsString(newArticle);

        //when
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/articles")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


        //then
        List<Article> addArticle = articleRepository.findAll();
        assertEquals(7, addArticle.size());
    }

    @Test
    @DisplayName("PUT - update Article -> HTTP 200")
    void shouldUpdateArticle() throws Exception {
        //given
        Article newArticle = new Article(11L,"Programing Language","Programing", LocalDateTime.now().toLocalDate(), "ProgramingFuture",
                "Paul Martin", new Timestamp(100, 10, 11, 0, 0, 0, 0));

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = objectMapper.writeValueAsString(newArticle);

        //when
        final var resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/articles/1")
                                .contentType("application/json")
                                .accept("application/json")
                                .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


        //then
        List<Article> updateArticle = articleRepository.findArticleById(1L);
        assertEquals("Programing Language", updateArticle.get(0).getDescribeText());
    }

    @Test
    @DisplayName("DELETE - delete Article -> HTTP 204")
    void shouldDeleteArticle() throws Exception {
        //given
        String articleIdToDelete = "1";

        //when
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/articles/" + articleIdToDelete)

                )
                .andDo(print())
                .andExpect(status().is(202));

        //then
        List<Article> existArticle = articleRepository.findAll();
        assertEquals(5, existArticle.size());
    }
}