package org.othertwink.pageablebooks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.pageablebooks.model.entity.Author;
import org.othertwink.pageablebooks.repository.AuthorRepo; // Не забудьте создать репозиторий для Author
import org.othertwink.pageablebooks.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepo authorRepo;

    public AuthorServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    @Transactional
    public Author createAuthor(String name) {
        Author author = Author.builder()
                .name(name)
                .build();
        return authorRepo.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, String name) {
        Author existingAuthor = findById(id);
        existingAuthor.setName(name);
        return authorRepo.save(existingAuthor);
    }

    @Override
    @Transactional
    public Author deleteAuthor(Long id) {
        Author author = findById(id);
        authorRepo.delete(author);
        return author;
    }
}
