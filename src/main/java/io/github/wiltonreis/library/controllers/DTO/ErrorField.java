package io.github.wiltonreis.library.controllers.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Campo de erro", description = "Representa um campo de erro")
public record ErrorField(String field, String error) {
}
