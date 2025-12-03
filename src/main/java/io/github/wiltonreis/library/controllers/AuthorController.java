package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.AuthorDTO;
import io.github.wiltonreis.library.controllers.mappers.AuthorMapper;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.security.SecurityService;
import io.github.wiltonreis.library.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController{

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        Author author = authorMapper.toEntity(authorDTO);
        Author authorSaved = authorService.saveAuthor(author);

        URI uri = generateHeaderLocation(authorSaved.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable String id){
        UUID idAuthor = UUID.fromString(id);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        return authorService.getAuthor(idAuthor)
                .map(author ->
                {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);
                    return ResponseEntity.ok(authorDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id){

        UUID idAuthor = UUID.fromString(id);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        if (authorOptional.isEmpty()) return ResponseEntity.notFound().build();

        authorService.deleteAuthor(idAuthor);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    public ResponseEntity<List<AuthorDTO>> filterAuthor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality){


        List<Author> authors = authorService.filterAuthor(name, nationality);
        List<AuthorDTO> result = authors.stream()
                .map(authorMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> updateAuthor(@PathVariable String id, @RequestBody @Valid AuthorDTO authorDTO){
        UUID idAuthor = UUID.fromString(id);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        if (authorOptional.isEmpty()) return ResponseEntity.notFound().build();

        Author author = authorOptional.get();

        author.setName(authorDTO.name());
        author.setBirthDate(authorDTO.birthDate());
        author.setNationality(authorDTO.nationality());

        authorService.updateAuthor(author);
        return ResponseEntity.noContent().build();
    }


}
