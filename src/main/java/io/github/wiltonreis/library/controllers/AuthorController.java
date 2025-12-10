package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.AuthorDTO;
import io.github.wiltonreis.library.controllers.mappers.AuthorMapper;
import io.github.wiltonreis.library.model.Author;
import io.github.wiltonreis.library.security.SecurityService;
import io.github.wiltonreis.library.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(name = "Autores")
@Slf4j
public class AuthorController implements GenericController{

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Salvar", description = "Cadastra novo autor no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")

    })
    public ResponseEntity<Void> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO){

        log.info("Salvando autor: {}", authorDTO.name());

        Author author = authorMapper.toEntity(authorDTO);
        Author authorSaved = authorService.saveAuthor(author);

        URI uri = generateHeaderLocation(authorSaved.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Buscar", description = "Busca autor pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
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
    @Operation(summary = "Deletar", description = "Deleta autor pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado e não pode ser deletado")
    })
    public ResponseEntity<Object> deleteAuthor(@PathVariable String id){

        UUID idAuthor = UUID.fromString(id);

        log.info("Deletando autor de id: {}", idAuthor);

        Optional<Author> authorOptional = authorService.getAuthor(idAuthor);

        if (authorOptional.isEmpty()) return ResponseEntity.notFound().build();

        authorService.deleteAuthor(idAuthor);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
    @Operation(summary = "Pesquisar", description = "Pesquisa autor pelo nome e/ou nacionalidade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autores encontrados")
    })
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
    @Operation(summary = "Atualizar", description = "Atualiza autor pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")

    })
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
