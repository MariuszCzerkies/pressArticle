package com.example.pressarticle.article;

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
    @DisplayName("GET /articleAll -> HTTP 200, sorted list articles from base")
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

        List<Long> expectedSortId = new ArrayList<>();
        for (int i = 0; i < articleFromDBAsJava.size(); i++) {
            expectedSortId.add(articleFromDBAsJava.get(i).getId());
        }

        //then
        assertEquals(exampleSortArticleId, expectedSortId);
    }

    @Test
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
    void shouldUpdateArticle() throws Exception {
        //given
        // todo zapisać artykuł do późniejszej aktualizacji - użyć - articleRepository.save

        Article newArticle = new Article(11L,"Programing Language","Programing", LocalDateTime.now().toLocalDate(), "ProgramingFuture",
                "Paul Martin", new Timestamp(100, 10, 11, 0, 0, 0, 0));

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson= objectMapper.writeValueAsString(newArticle);

       // Article save = articleRepository.save(newArticle);

        //when
        final var resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/articles/articleUpdate/1")
                                .contentType("application/json")
                                .accept("application/json")
                                .content(requestJson)
                        //todo dodąć body (content) tak jak przy zapisie
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());


        //then
        List<Article> updateArticle = articleRepository.findArticleById(1L);
        assertEquals("Programing Language", updateArticle.get(0).getDescribeText());
        //assertEquals("NewUpdate", updateArticle.get(0).getNameMagazine());
    }

    @Test
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