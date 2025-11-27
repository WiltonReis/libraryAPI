package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.AuthorDTO;
import io.github.wiltonreis.library.controllers.DTO.ViewAuthorDTO;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorDTO authorDTO){

        Author author = authorDTO.toAuthor();
        Author authorSaved = authorService.saveAuthor(author);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorSaved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
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
    public ResponseEntity<Void> deleteAuthor(@PathVariable String id){
        UUID idAuthor = UUID.fromString(id);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        if(authorOptional.isEmpty()) return ResponseEntity.notFound().build();

        authorService.deleteAuthor(idAuthor);
        return ResponseEntity.ok().build();
    }

}
