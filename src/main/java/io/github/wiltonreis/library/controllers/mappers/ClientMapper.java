package io.github.wiltonreis.library.controllers.mappers;

import io.github.wiltonreis.library.controllers.DTO.ClientDTO;
import io.github.wiltonreis.library.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client client);

    Client toEntity(ClientDTO clientDTO);
}
