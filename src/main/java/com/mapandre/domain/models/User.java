package com.mapandre.domain.models;

import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_users")
public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long databaseId;
    @Column(nullable = false, unique = true)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID externalId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDate birthDate;

    /**
     *  Default constructor for hibernate
     * */
    public User() {
    }

    public User(UUID externalId, String firstName, String lastName, String cpf, String password, LocalDate birthDate) {
        this.externalId = externalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.password = password;
        this.birthDate = birthDate;
    }

    public long getDatabaseId() {
        return databaseId;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return externalId.equals(user.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId);
    }
}
