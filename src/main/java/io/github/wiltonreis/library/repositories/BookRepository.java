package io.github.wiltonreis.library.repositories;

import io.github.wiltonreis.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
