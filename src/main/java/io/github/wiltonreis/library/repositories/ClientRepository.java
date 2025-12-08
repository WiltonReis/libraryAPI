package io.github.wiltonreis.library.repositories;

import io.github.wiltonreis.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {


    Optional<Client> findByClientId(String clientId);
}
