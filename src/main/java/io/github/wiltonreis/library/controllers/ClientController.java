package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.ClientDTO;
import io.github.wiltonreis.library.controllers.mappers.ClientMapper;
import io.github.wiltonreis.library.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    public void save(@RequestBody ClientDTO clientDTO) {
        clientService.save(clientMapper.toEntity(clientDTO));
    }
}
