package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.UserDTO;
import io.github.wiltonreis.library.controllers.mappers.UserMapper;
import io.github.wiltonreis.library.model.User;
import io.github.wiltonreis.library.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        userService.save(user);
    }
}
