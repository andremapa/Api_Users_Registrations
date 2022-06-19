package com.mapandre.domain.dto.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserRequestDto {

    @NotEmpty
    @Length(min = 3, max = 12)
    private final String firstName;
    @NotEmpty
    @Length(min = 3, max = 12)
    private final String lastName;
    @CPF
    @NotEmpty
    private final String cpf;
    @NotEmpty
    @Length(min = 6, max = 12)
    private final String password;
    @NotNull
    //TODO: LOOK SOME CONSTRAINTS
    private final LocalDate birthDate;

    public UserRequestDto(String firstName, String lastName, String cpf, String password, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.password = password;
        //TODO:org.springframework.http.converter.HttpMessageNotReadableException
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
