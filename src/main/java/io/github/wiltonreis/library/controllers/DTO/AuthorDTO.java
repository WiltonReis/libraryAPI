package io.github.wiltonreis.library.controllers.DTO;

import io.github.wiltonreis.library.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorDTO(
        @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters")
        @NotBlank(message = "The name cannot be null")
        String name,
        @Past(message = "The date of birth must be in the past")
        @NotNull(message = "Date of birth cannot be null")
        LocalDate birthDate,
        @Size(min = 3, max = 50, message = "The nationality must have between 3 and 50 characters")
        @NotBlank(message = "The nationality cannot be null")
        String nationality
) {

    public Author toAuthor(){
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
        return author;
    }
}
