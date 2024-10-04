package org.othertwink.pageablebooks.service;

import org.othertwink.pageablebooks.model.entity.Author;
import org.othertwink.pageablebooks.model.entity.Book;

public interface AuthorService {
    Author createAuthor(String name);
    Author deleteAuthor(Long id);
    Author updateAuthor(Long id, String name);
    Author findById(Long id);
}
