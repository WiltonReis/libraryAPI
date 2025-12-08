package io.github.wiltonreis.library.services;

import io.github.wiltonreis.library.model.Client;
import io.github.wiltonreis.library.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        return clientRepository.save(client);
    }

    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId).orElse(null);
    }
}
