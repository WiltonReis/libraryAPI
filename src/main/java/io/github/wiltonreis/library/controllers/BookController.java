package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.BookRegistrationDTO;
import io.github.wiltonreis.library.controllers.DTO.BookSearchResultDTO;
import io.github.wiltonreis.library.controllers.mappers.BookMapper;
import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.model.GenreBook;
import io.github.wiltonreis.library.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OPERATOR', 'MANAGER')")
@Tag(name = "Livros")
public class BookController implements GenericController{

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @Operation(summary = "Salvar", description = "Cadastra novo livro no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado")

    })
    public ResponseEntity<Void> saveBook(@RequestBody @Valid BookRegistrationDTO bookDTO){
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookService.save(book);

        URI uri = generateHeaderLocation(savedBook.getId());

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Busca livro pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<BookSearchResultDTO> getBook(@PathVariable String id){
        UUID idBook = UUID.fromString(id);

        Optional<Book> bookOptional = bookService.getbook(idBook);

        return bookOptional.map(book -> {
            BookSearchResultDTO bookDTO = bookMapper.toDTO(book);
            return ResponseEntity.ok(bookDTO);
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deleta livro pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Object> deleteBook(@PathVariable String id){
        UUID idBook = UUID.fromString(id);

        Optional<Book> bookOptional = bookService.getbook(idBook);

        return bookOptional.map(book -> {
            bookService.deleteBook(idBook);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Pesquisar", description = "Pesquisa livro pelo isbn, titulo, autor e/ou genero")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livros encontrados")
    })
    public ResponseEntity<Page<BookSearchResultDTO>> filterBook(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) GenreBook genre,
            @RequestParam(value = "publication-year", required = false) Integer publicationYear,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size

    ){
        Page<Book> pages = bookService.filterBook(isbn, title, authorName, genre, publicationYear, page, size);

        Page<BookSearchResultDTO> result = pages.map(bookMapper::toDTO);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar", description = "Atualiza livro pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado")
    })
    public ResponseEntity<Object> updateBook(@PathVariable String id, @RequestBody @Valid BookRegistrationDTO bookDTO){
        UUID idBook = UUID.fromString(id);

        Optional<Book> bookOptional = bookService.getbook(idBook);

        return bookOptional.map(book -> {
            Book entityAux = bookMapper.toEntity(bookDTO);

            book.setIsbn(entityAux.getIsbn());
            book.setTitle(entityAux.getTitle());
            book.setGenre(entityAux.getGenre());
            book.setPublicationDate(entityAux.getPublicationDate());
            book.setAuthor(entityAux.getAuthor());

            bookService.updateBook(book);
            return ResponseEntity.noContent().build();

        }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
