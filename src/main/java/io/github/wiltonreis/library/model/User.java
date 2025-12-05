package io.github.wiltonreis.library.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String login;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 150)
    private String email;

    @Type(ListArrayType.class)
    @Column(nullable = false, columnDefinition = "varchar[]")
    private List<String> roles;
}
