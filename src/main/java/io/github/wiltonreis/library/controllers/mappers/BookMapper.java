package io.github.wiltonreis.library.controllers.mappers;

import io.github.wiltonreis.library.controllers.DTO.BookRegistrationDTO;
import io.github.wiltonreis.library.controllers.DTO.BookSearchResultDTO;
import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.repositories.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    AuthorRepository repository;

    @Mapping(target = "author", expression = "java( repository.findById(dto.authorId()).orElse(null) )")
    public abstract Book toEntity(BookRegistrationDTO dto);

    public abstract BookSearchResultDTO toDTO(Book entity);
}
