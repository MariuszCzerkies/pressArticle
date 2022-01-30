package com.example.pressarticle.article;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
//import java.time.Instant; new Instant.parse
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
                .perform(MockMvcRequestBuilders.get("/article/articles"))
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
                .perform(MockMvcRequestBuilders.get("/article/articleId/1"))
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
                .perform(MockMvcRequestBuilders.get("/article/articleText?text=New World&titleText=World"))
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(newArticle);


        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/article/articleAdd")
                        //.header("Content-Type", "application/json")
                        .contentType("application/json")
                        .header("Accept", "application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        //when
        List<Article> addArticle = articleRepository.findAll();

        //then
        assertEquals(7, addArticle.size());
   }

    @Test
    void shouldUpdateArticle() throws Exception {
        //give
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders.put("/article/articleUpdate/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> updateArticle = articleRepository.findArticleById(1L);

        //then
        assertEquals("Update", updateArticle.get(0).getDescribeText());
        assertEquals("NewUpdate", updateArticle.get(0).getNameMagazine());
    }

    @Test
    void shouldDeleteArticle() throws Exception {
        //give
        final var resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/article/articleDelete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        List<Article> existArticle = articleRepository.findAll();

        //then
        assertEquals(5, existArticle.size());
    }
}