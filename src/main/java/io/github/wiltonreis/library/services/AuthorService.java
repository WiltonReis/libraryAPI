package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.exception.OperationNotAllowed;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.repositories.AuthorRepository;
import io.github.wiltonreis.library.repositories.BookRepository;
import io.github.wiltonreis.library.validators.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;

    public Author saveAuthor(Author author){
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public void updateAuthor(Author author){
        if(author.getId() == null) throw new IllegalArgumentException("Author not registered");
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public Optional<Author> getAuthor(UUID id){
        return authorRepository.findById(id);
    }

    public void deleteAuthor(UUID id){
        if(hasBook(id)) throw new OperationNotAllowed("It is not possible to delete an author who has books.");
        authorRepository.deleteById(id);
    }

    public List<Author> filterAuthor(String name, String nationality){

        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


        Example<Author> authorExample = Example.of(author, matcher);

        return authorRepository.findAll(authorExample);
    }

    private boolean hasBook(UUID id){
        return bookRepository.existsByAuthorId(id);
    }


}
