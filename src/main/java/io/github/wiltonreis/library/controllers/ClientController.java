package io.github.wiltonreis.library.controllers;

import io.github.wiltonreis.library.controllers.DTO.ClientDTO;
import io.github.wiltonreis.library.controllers.mappers.ClientMapper;
import io.github.wiltonreis.library.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Salvar", description = "Cadastra novo cliente no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client cadastrado com sucesso")

    })
    public void save(@RequestBody ClientDTO clientDTO) {
        log.info("Salvando clnovo cient {}, com scope {}", clientDTO.clientId(), clientDTO.scope());
        clientService.save(clientMapper.toEntity(clientDTO));
    }
}
