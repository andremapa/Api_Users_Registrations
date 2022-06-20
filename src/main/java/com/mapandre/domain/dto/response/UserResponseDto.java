package com.mapandre.domain.dto.response;

import com.mapandre.domain.models.User;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private final UUID externalId;
    private final String firstName;
    private final String lastName;
    private final String cpf;
    private final String password;
    private final LocalDate birthDate;

    public UserResponseDto(@NotNull User user) {
        this.externalId = user.getExternalId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.cpf = user.getCpf();
        this.password = user.getPassword();
        this.birthDate = user.getBirthDate();
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
}
