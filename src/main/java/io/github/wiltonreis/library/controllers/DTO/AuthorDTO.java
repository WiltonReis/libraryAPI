package io.github.wiltonreis.library.controllers.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters")
        @NotBlank(message = "required field")
        String name,
        @Past(message = "The date of birth must be in the past")
        @NotNull(message = "required field")
        LocalDate birthDate,
        @Size(min = 3, max = 50, message = "The nationality must have between 3 and 50 characters")
        @NotBlank(message = "required field")
        String nationality
) {
}
