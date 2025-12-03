package io.github.wiltonreis.library.controllers.DTO;

import java.util.List;

public record UserDTO(String login, String password, List<String> roles) {
}
