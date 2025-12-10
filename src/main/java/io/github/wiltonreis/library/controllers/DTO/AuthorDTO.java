package io.github.wiltonreis.library.controllers.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor", description = "Representa um autor que será cadastrado ou retornado")
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
