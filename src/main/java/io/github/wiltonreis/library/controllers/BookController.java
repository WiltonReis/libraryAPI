package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.BookRegistrationDTO;
import io.github.wiltonreis.library.controllers.DTO.BookSearchResultDTO;
import io.github.wiltonreis.library.controllers.mappers.BookMapper;
import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController{

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody @Valid BookRegistrationDTO bookDTO){
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookService.save(book);

        URI uri = generateHeaderLocation(savedBook.getId());

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookSearchResultDTO> getBook(@PathVariable String id){
        UUID idBook = UUID.fromString(id);

        Optional<Book> bookOptional = bookService.getbook(idBook);

        return bookOptional.map(book -> {
            BookSearchResultDTO bookDTO = bookMapper.toDTO(book);
            return ResponseEntity.ok(bookDTO);
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }
    
    public ResponseEntity<Object> deleteBook(@PathVariable String id){
        UUID idBook = UUID.fromString(id);

        Optional<Book> bookOptional = bookService.getbook(idBook);

        return bookOptional.map(book -> {
            bookService.deleteBook(idBook);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
