package org.othertwink.pageablebooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.othertwink.pageablebooks.model.entity.Author;
import org.othertwink.pageablebooks.model.entity.Book;
import org.othertwink.pageablebooks.service.AuthorService;
import org.othertwink.pageablebooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthorService authorService;

    @MockBean
    BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks_ShouldReturnBooksPage() throws Exception {
        List<Book> books = List.of(
                new Book(1L, 1L, "Title1", "Text1"),
                new Book(2L, 1L, "Title2", "Text2")
        );

        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 10), books.size());

        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(bookPage);

        mockMvc.perform(get("/library/books/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookPage)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title").value("Title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2));

    }

    @Test
    void testCreateAuthor() throws Exception {
        Author author = Author.builder().name("Иван Иванов").build();

        when(authorService.createAuthor(anyString())).thenReturn(author);

        mockMvc.perform(post("/library/authors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).createAuthor(author.getName());
    }

    @Test
    void testCreateAuthorBad() throws Exception {
        Author author = Author.builder().name("Иван Иванов").build();

        when(authorService.createAuthor(anyString())).thenReturn(author);

        mockMvc.perform(post("/library/authors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).createAuthor(author.getName());
    }

    @Test
    void testUpdateAuthor() throws Exception {
        Author author = Author.builder().id(1L).name("Иван Иванов").build();

        when(authorService.updateAuthor(anyLong(), anyString())).thenReturn(author);

        mockMvc.perform(put("/library/authors/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).updateAuthor(author.getId(), author.getName());
    }

    @Test
    void createBook() throws Exception {
        Book book = Book.builder()
                .authorId(1L)
                .title("Lorem ipsum")
                .text("A very smart and hilariously simple famous quote")
                .build();

        when(bookService.createBook(anyLong(),anyString(),anyString())).thenReturn(book);

        mockMvc.perform(post("/library/books/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authorId").value(book.getAuthorId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.text").value(book.getText()));

        verify(bookService, times(1)).createBook(book.getAuthorId(),book.getTitle(),book.getText());

    }
}
