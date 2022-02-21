package com.example.pressarticle.article;

import com.example.pressarticle.article.controller.dto.ArticleDto;
import com.example.pressarticle.article.controller.dto.ArticleResponse;
import com.example.pressarticle.article.domain.model.Article;
import com.example.pressarticle.article.external.repository.JpaArticleRepository;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc//zezwala na MockMvc
@Transactional//w testach po każdym teście wycofuje zmiany zrobione na bazie ktore odbyly sie w trakcie wykonywania tego testu
class ArticleControllerTest {

    @Autowired
    private JpaArticleRepository jpaArticleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET - get Sorted All Articles -> HTTP 200, sorted list articles from base")
    void shouldSortAllArticle() throws Exception {
        //given
        List<ArticleDto> expectedList = List.of (
                new ArticleDto(2L, "Machines replace human","Machines", LocalDate.of(2015,10,11), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(3L, "Robots are a system computer","Robots", LocalDate.of(2014,8,21), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(4L, "Electrical 230VAC 24VDC","Electrical", LocalDate.of(2012,7,8), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(6L, "New World is for you","NewWorld", LocalDate.of(2011,9,17), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(5L, "New World is for you","NewWorld", LocalDate.of(2010,9,17), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(1L, "New World is for you","NewWorld", LocalDate.of(1022,9,10), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")));

        //when
        final var responseBodyAsString = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles"))
                .andDo(print())
                .andExpect(status().isOk());

        final var articleFormDBInJSON = responseBodyAsString.andReturn().getResponse().getContentAsString();
        List<ArticleDto> responseBody = objectMapper.readValue(articleFormDBInJSON, new TypeReference<>() {});

         //then
        assertEquals(expectedList.size(), responseBody.size());
        IntStream.range(0, expectedList.size()).forEach(i -> assertEquals(expectedList.get(i).getDataPublication(), responseBody.get(i).getDataPublication()));
    }

    @Test
    @DisplayName("GET - get Article By Id -> HTTP 201")
    void shouldFindArticleById() throws Exception {
        //given
        Long articleId = 1L;

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles/" + articleId))
                .andDo(print())
                .andExpect(status().isOk());

        final var articleFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        ArticleDto responseBody = objectMapper.readValue(articleFormDBInJSON, new TypeReference<>() {});

        //then
        assertEquals(1, responseBody.getId());
        assertEquals(LocalDate.of(1022,9,10), responseBody.getDataPublication());
        assertEquals("World", responseBody.getNameMagazine());
        assertEquals("New World is for you", responseBody.getDescribeText());
        assertEquals(Instant.parse("2018-08-22T10:00:00Z"), responseBody.getDataSaveDocument());
        assertEquals("Allan Balkier", responseBody.getNameAuthor());
        assertEquals("NewWorld", responseBody.getTitleText());
    }

    @Test
    @DisplayName("GET - get All Article By Description Or Title -> HTTP 200")
    void shouldGetAllArticlesDescription() throws Exception {
        //given
        String articleDescribe = "search?description=New World&title=Electrical";

        List<ArticleDto> expectedList = List.of (
                new ArticleDto(4L, "Electrical 230VAC 24VDC","Electrical", LocalDate.of(2012,7,8), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(6L, "New World is for you","NewWorld", LocalDate.of(2011,9,17), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(5L, "New World is for you","NewWorld", LocalDate.of(2010,9,17), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z")),
                new ArticleDto(1L, "New World is for you","NewWorld", LocalDate.of(1022,9,10), "World", "Allan Balkier", Instant.parse("2018-08-22T10:00:00Z"))
        );

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/articles/" + articleDescribe))
                .andDo(print())
                .andExpect(status().isOk());

        final var articleFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        ArticleResponse responseBody = objectMapper.readValue(articleFormDBInJSON, new TypeReference<>() {});

        //then
        assertEquals(expectedList.size(), responseBody.getArticles().size());
    }

    @Test
    @DisplayName("POST - add All Article By Description Or Title -> HTTP 201")
    void shouldAddArticle() throws Exception {
        //give
        ArticleDto newArticle = new ArticleDto(11L,"Programing Language","Programing", LocalDateTime.now().toLocalDate(), "ProgramingFuture",
                "Paul Martin", Instant.parse("2018-08-22T10:00:00Z"));

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson= objectMapper.writeValueAsString(newArticle);

        //when
        final var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/articles")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().is(201));

        final var locationHeaderValue = response.andReturn().getResponse().getHeader("Location");

        //then
        assertEquals("http://localhost/articles/7", locationHeaderValue);
    }

    @Test
    @DisplayName("PUT - update Article -> HTTP 200")
    void shouldUpdateArticle() throws Exception {
        //given
        Long articleId = 1L;
        ArticleDto newArticle = new ArticleDto(1L,"Programing Language","Programing", LocalDateTime.now().toLocalDate(), "ProgramingFuture",
                "Paul Martin", Instant.parse("2018-08-22T10:00:00Z"));

//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = objectMapper.writeValueAsString(newArticle);

        //when
        final var response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/articles/" + articleId)
                                .contentType("application/json")
                                .accept("application/json")
                                .content(requestJson)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        final var articleFormDBInJSON = response.andReturn().getResponse().getContentAsString();
        ArticleDto responseBody = objectMapper.readValue(articleFormDBInJSON, new TypeReference<>() {});

        //then
        assertEquals(newArticle.getDataPublication(), responseBody.getDataPublication());
    }

    @Test
    @DisplayName("DELETE - delete Article -> HTTP 202")
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
        List<Article> existArticle = jpaArticleRepository.findAll();
        assertEquals(5, existArticle.size());
    }
}