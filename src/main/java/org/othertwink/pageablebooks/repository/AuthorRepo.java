package org.othertwink.pageablebooks.repository;

import org.othertwink.pageablebooks.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);
}
