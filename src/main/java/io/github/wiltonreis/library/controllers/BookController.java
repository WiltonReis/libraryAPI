package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.BookRegistrationDTO;
import io.github.wiltonreis.library.controllers.DTO.ErrorResponse;
import io.github.wiltonreis.library.controllers.mappers.BookMapper;
import io.github.wiltonreis.library.exception.DuplicatedRecordException;
import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookRegistrationDTO bookDTO){
        try {
            Book book = bookMapper.toEntity(bookDTO);
            Book savedBook = bookService.save(book);

            return ResponseEntity.ok(savedBook);
        } catch (DuplicatedRecordException e) {
            ErrorResponse conflict = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(conflict.status()).body(conflict);
        }
    }
}
