package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Author> filterAuthor(String name, String nationality){

        if(name != null && nationality != null) return authorRepository.findByNameAndNationality(name, nationality);

        if(name != null) return authorRepository.findByName(name);

        if(nationality != null) return authorRepository.findByNationality(nationality);

        return authorRepository.findAll();
    }

}
