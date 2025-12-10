package io.github.wiltonreis.library.controllers.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Client", description = "Representa um cliente que será cadastrado ou retornado")
public record ClientDTO(String clientId, String clientSecret, String redirectUri, String scope) {
}
