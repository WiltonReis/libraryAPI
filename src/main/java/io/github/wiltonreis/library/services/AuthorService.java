package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(UUID id){
        return authorRepository.findById(id);
    }

    public void deleteAuthor(UUID id){
        authorRepository.deleteById(id);
    }

}
