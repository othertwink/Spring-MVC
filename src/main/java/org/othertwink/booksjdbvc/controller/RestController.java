package org.othertwink.booksjdbvc.controller;

import org.othertwink.booksjdbvc.model.entity.Book;
import org.othertwink.booksjdbvc.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RestController {

    private final BookService bookService;
    
    public RestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/catalog")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookDetails(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PostMapping(value = "/books/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping(value = "/books/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book createdOrder = bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(createdOrder);
    }

}
