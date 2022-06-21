package com.mapandre.domain.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mapandre.domain.models.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
    private final LocalDate birthDate;

    public UserRequestDto(String firstName, String lastName, String cpf, String password, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.password = password;
        this.birthDate = LocalDate.parse(birthDate);
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

    public User convertToModel(){
        return new User(UUID.randomUUID(), this.firstName, this.lastName, this.cpf, this.password, this.getBirthDate());
    }

    public static class Builder{

        private String firstName;
        private String lastName;
        private String cpf;
        private String password;
        private String birthDate;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withCpf(String cpf){
            this.cpf = cpf;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }

        public Builder withBirthDate(String birthDate){
            this.birthDate = birthDate;
            return this;
        }

        public UserRequestDto build(){
            return new UserRequestDto(firstName, lastName, cpf, password, birthDate);
        }
    }
}
