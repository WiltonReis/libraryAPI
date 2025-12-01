package io.github.wiltonreis.library.validators;

import io.github.wiltonreis.library.exception.DuplicatedRecordException;
import io.github.wiltonreis.library.exception.InvalidFieldException;
import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final int demandingYear = 2020;

    private final BookRepository repository;

    public void validate(Book book){
        if (existsByIsbn(book)) throw new DuplicatedRecordException("Book already exists");
        if (isMandatoryPriceNull(book)) throw new InvalidFieldException("price", "For books published after 2020, the price is mandatory.");
    }

    private boolean isMandatoryPriceNull(Book book) {
        return book.getPrice() == null &&
                book.getPublicationDate().getYear() >= demandingYear;
    }

    private boolean existsByIsbn(Book book) {
        Optional<Book> bookOptional = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null) return bookOptional.isPresent();

        return bookOptional
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
