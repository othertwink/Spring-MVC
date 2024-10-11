package org.othertwink.booksjdbvc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.booksjdbvc.model.entity.Book;
import org.othertwink.booksjdbvc.repository.BookRepo;
import org.othertwink.booksjdbvc.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    @Transactional
    public void createBook(Book book) {
        Book createdBook = Book.builder()
                .authorId(book.getAuthorId())
                .title(book.getTitle())
                .build();
        bookRepo.save(createdBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        Book existingBook = findById(book.getId());
        existingBook.setAuthorId(book.getAuthorId());
        existingBook.setTitle(book.getTitle());
        bookRepo.save(existingBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    @Transactional
    public Book deleteBook(Long id) {
        Book book = findById(id);
        bookRepo.deleteById(id);
        return book;
    }
}
