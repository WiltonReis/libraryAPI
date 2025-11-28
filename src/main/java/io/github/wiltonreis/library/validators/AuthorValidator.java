package io.github.wiltonreis.library.validators;

import io.github.wiltonreis.library.exception.DuplicatedRecordException;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author){
        if(thereIsAuthor(author)) throw new DuplicatedRecordException("Author already exists");
    }


    private boolean thereIsAuthor(Author author){

        Optional<Author> authorOptional = authorRepository.findByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );

        if (author.getId() == null) return authorOptional.isPresent();

        return !author.getId().equals(authorOptional.get().getId()) && authorOptional.isPresent();
    }
}
