package org.othertwink.booksjdbvc.repository;

import org.othertwink.booksjdbvc.model.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepo{
    private final JdbcTemplate jdbcTemplate;

    public BookRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Book(rs.getLong("id"), rs.getString("title"), rs.getLong("authorId"))
        );
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new Book(rs.getLong("id"), rs.getString("title"), rs.getLong("authorId"))
        ).stream().findFirst();
    }

    public void save(Book book) {
        String sql = "INSERT INTO books (title, authorId) VALUES (?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthorId());
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, authorId = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthorId(), book.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
