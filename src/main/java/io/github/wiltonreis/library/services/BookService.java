package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.model.GenreBook;
import io.github.wiltonreis.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.wiltonreis.library.repositories.specs.BookSpecs.*;

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

    public List<Book> filterBook(String isbn,  String title, String authorName, GenreBook genre, Integer publicationYear){
        Specification<Book> spec = ((root, query, cb) -> cb.conjunction());

        if(isbn != null) spec = spec.and(isbnEqual(isbn));

        if(title != null) spec = spec.and(titleLike(title));

        if(authorName != null) spec = spec.and(genreEqual(authorName));

        if(genre != null) spec = spec.and(genreEqual(genre));

        if(publicationYear != null) spec = spec.and(publicationYearEqual(publicationYear));

        return bookRepository.findAll(spec);
    }
}
