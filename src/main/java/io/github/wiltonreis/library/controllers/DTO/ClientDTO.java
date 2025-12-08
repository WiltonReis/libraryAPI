package io.github.wiltonreis.library.controllers.DTO;

public record ClientDTO(String clientId, String clientSecret, String redirectUri, String scope) {
}
