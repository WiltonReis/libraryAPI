package io.github.wiltonreis.library.controllers.DTO;

import io.github.wiltonreis.library.model.Author;

import java.time.LocalDate;

public record AuthorDTO(String name,
                        LocalDate birthDate,
                        String nationality) {

    public Author toAuthor(){
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
        return author;
    }
}
