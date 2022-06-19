package com.mapandre.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_users")
public class User {

    //TODO: CHANGE EXTERNAL ID TO UUID

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long databaseId;
    @Column(nullable = false, unique = true)
    private long externalId;
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

    public User(long externalId, String firstName, String lastName, String cpf, String password, LocalDate birthDate) {
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

    public long getExternalId() {
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
}
