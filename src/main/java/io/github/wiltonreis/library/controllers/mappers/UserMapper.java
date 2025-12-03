package io.github.wiltonreis.library.controllers.mappers;

import io.github.wiltonreis.library.controllers.DTO.UserDTO;
import io.github.wiltonreis.library.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);
}
