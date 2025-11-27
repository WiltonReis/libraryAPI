package io.github.wiltonreis.library.controllers.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record ViewAuthorDTO(
        UUID userId,
        String name,
        LocalDate birthDate,
        String nationality
) {
}
