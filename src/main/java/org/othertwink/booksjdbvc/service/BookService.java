package org.othertwink.booksjdbvc.service;

import org.othertwink.booksjdbvc.model.entity.Book;

import java.util.List;

public interface BookService {
    void createBook(Book book);
    Book deleteBook(Long id);
    void updateBook(Book book);
    Book findById(Long id);
    List<Book> getAllBooks();
}
