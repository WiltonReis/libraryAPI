package io.github.wiltonreis.library.controllers.mappers;

import io.github.wiltonreis.library.controllers.DTO.AuthorDTO;
import io.github.wiltonreis.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author entity);
}
