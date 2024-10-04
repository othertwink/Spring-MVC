package org.othertwink.pageablebooks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.pageablebooks.model.entity.Book;
import org.othertwink.pageablebooks.repository.BookRepo;
import org.othertwink.pageablebooks.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {

    private BookRepo bookRepo;

    @Override
    @Transactional
    public Book createBook(Long authorId, String title, String text) {
        Book book = Book.builder()
                .authorId(authorId)
                .title(title)
                .text(text)
                .build();
        return bookRepo.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Long authorId, String title, String text) {
        Book existingBook = findById(id);
        existingBook.setAuthorId(authorId);
        existingBook.setTitle(title);
        existingBook.setText(text);
        return bookRepo.save(existingBook);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    @Override
    @Transactional
    public Book deleteBook(Long id) {
        Book book = findById(id);
        bookRepo.delete(book);
        return book;
    }
}
