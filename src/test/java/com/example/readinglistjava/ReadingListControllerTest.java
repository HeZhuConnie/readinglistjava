package com.example.readinglistjava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReadingListController.class)
class ReadingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReadingListRepository readingListRepository;

    @Test
    void readersBooks() throws Exception {
        mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    void addToReadingList() throws Exception {
        mockMvc.perform(post("/readingList")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "small hands")
                .param("author", "Martin Darline")
                .param("isbn", "4543567923788")
                .param("description", "no extra description")
        ).andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/readingList"));

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setTitle("small hands");
        expectedBook.setAuthor("Martin Darline");
        expectedBook.setIsbn("4543567923788");
        expectedBook.setDescription("no extra description");

        /*mockMvc.perform(get("/readingList"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));*/
    }
}