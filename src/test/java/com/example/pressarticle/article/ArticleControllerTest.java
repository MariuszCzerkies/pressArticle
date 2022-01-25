package com.example.pressarticle.article;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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
    void findAllArticle() throws Exception {
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
    void sortAll() throws Exception {
        //given
        final var resoultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/sort"))
                .andDo(print())
                .andExpect(status().isOk());

        //when
        //ResponseEntity<Article> response = callGetUser(user.getEmail(), token);
        List<Article> sortArticle = articleRepository.findArticleByQuery();

        //then
       // assertEquals(, sortArticle);
    }

    @Test
    void article() throws Exception {
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
    void articleTitle() {
    }

    @Test
    void addArticle() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticle() {
    }
}