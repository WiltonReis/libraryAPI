package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getbook(UUID idBook) {
        return bookRepository.findById(idBook);
    }

    public void deleteBook(UUID idBook){
        bookRepository.deleteById(idBook);
    }
}
