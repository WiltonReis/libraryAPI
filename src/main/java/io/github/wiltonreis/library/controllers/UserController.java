package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.UserDTO;
import io.github.wiltonreis.library.controllers.mappers.UserMapper;
import io.github.wiltonreis.library.model.User;
import io.github.wiltonreis.library.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "Cadastra novo usuario no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
    })
    public void save(@RequestBody @Valid UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        userService.save(user);
    }
}
