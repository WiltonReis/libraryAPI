package io.github.wiltonreis.library.controllers.DTO;

import io.github.wiltonreis.library.model.GenreBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Registrar Livro", description = "Representa um livro que será cadastrado")
public record BookRegistrationDTO(
        @ISBN
        @NotBlank(message = "required field")
        String isbn,
        @NotBlank(message = "required field")
        String title,
        @Past(message = "The publication date must be in the past")
        @NotNull(message = "required field")
        LocalDate publicationDate,
        BigDecimal price,
        GenreBook genre,
        UUID authorId
) {

}
