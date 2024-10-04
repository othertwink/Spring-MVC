package org.othertwink.pageablebooks.repository;

import org.othertwink.pageablebooks.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    Optional<Book> findByAuthorId(Long authorId);
    Page<Book> findAll(Pageable pageable);

}
