package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
