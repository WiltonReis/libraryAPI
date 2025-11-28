package io.github.wiltonreis.library.controllers.DTO;

import io.github.wiltonreis.library.model.GenreBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookSearchResultDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BigDecimal price,
        GenreBook genre,
        AuthorDTO author
) {

}
