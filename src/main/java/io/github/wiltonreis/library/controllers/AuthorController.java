package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.AuthorDTO;
import io.github.wiltonreis.library.controllers.DTO.ErrorResponse;
import io.github.wiltonreis.library.controllers.DTO.ViewAuthorDTO;
import io.github.wiltonreis.library.exception.DuplicatedRecordException;
import io.github.wiltonreis.library.exception.OperationNotAllowed;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        try {
            Author author = authorDTO.toAuthor();
            Author authorSaved = authorService.saveAuthor(author);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorSaved.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        } catch (DuplicatedRecordException e){
            ErrorResponse error = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewAuthorDTO> getAuthor(@PathVariable String id){
        UUID idAuthor = UUID.fromString(id);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        if(authorOptional.isEmpty()) return ResponseEntity.notFound().build();

        Author author = authorOptional.get();
        ViewAuthorDTO viewAuthor = new ViewAuthorDTO(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );

        return ResponseEntity.ok(viewAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id){
        try {
            UUID idAuthor = UUID.fromString(id);

            Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

            if (authorOptional.isEmpty()) return ResponseEntity.notFound().build();

            authorService.deleteAuthor(idAuthor);
            return ResponseEntity.ok().build();
        } catch (OperationNotAllowed e){
            ErrorResponse erro = ErrorResponse.standardError(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }


    @GetMapping
    public ResponseEntity<List<ViewAuthorDTO>> filterAuthor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality){


        List<Author> authors = authorService.filterAuthor(name, nationality);
        List<ViewAuthorDTO> result = authors.stream().map(author -> new ViewAuthorDTO(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        )).toList();

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO){
        try{
            UUID idAuthor = UUID.fromString(id);

            Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

            if (authorOptional.isEmpty()) return ResponseEntity.notFound().build();

            Author author = authorOptional.get();

            author.setName(authorDTO.name());
            author.setBirthDate(authorDTO.birthDate());
            author.setNationality(authorDTO.nationality());

            authorService.updateAuthor(author);
            return ResponseEntity.noContent().build();
        } catch (DuplicatedRecordException e){
            ErrorResponse error = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }


}
