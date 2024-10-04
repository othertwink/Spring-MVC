package org.othertwink.pageablebooks.controller;

import org.othertwink.pageablebooks.model.entity.Author;
import org.othertwink.pageablebooks.model.entity.Book;
import org.othertwink.pageablebooks.service.AuthorService;
import org.othertwink.pageablebooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library")
public class RestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/books/catalog")
    public ResponseEntity<Page<Book>> getAllBooks(
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookDetails(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PostMapping(value = "/authors/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createAuthor = authorService.createAuthor(author.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }

    @PutMapping(value = "/authors/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        Author createAuthor = authorService.updateAuthor(author.getId(), author.getName());
        return ResponseEntity.status(HttpStatus.OK).body(createAuthor);
    }

    @PostMapping(value = "/books/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book.getAuthorId(), book.getTitle(), book.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping(value = "/books/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book createdBook = bookService.updateBook(book.getId(), book.getAuthorId(), book.getTitle(), book.getText());
        return ResponseEntity.status(HttpStatus.OK).body(createdBook);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book createdOrder = bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(createdOrder);
    }

}
