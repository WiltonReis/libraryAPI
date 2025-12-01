package io.github.wiltonreis.library.repositories;

import io.github.wiltonreis.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    boolean existsByAuthorId(UUID authorId);

    Optional<Book> findByIsbn(String isbn);
}
