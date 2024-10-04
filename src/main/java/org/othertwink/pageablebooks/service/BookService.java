package org.othertwink.pageablebooks.service;

import org.othertwink.pageablebooks.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book createBook(Long authorId, String title, String text);
    Book deleteBook(Long id);
    Book updateBook(Long id, Long authorId, String title, String text);
    Book findById(Long id);
    Page<Book> getAllBooks(Pageable pageable);
}
