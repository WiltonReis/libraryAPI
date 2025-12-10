package io.github.wiltonreis.library.controllers.DTO;

import io.github.wiltonreis.library.model.GenreBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Livro Resultado", description = "Representa um livro que será retornado")
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
