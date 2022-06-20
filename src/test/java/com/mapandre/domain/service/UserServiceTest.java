package com.mapandre.domain.service;

import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;
import com.mapandre.domain.repositories.UserRepository;
import com.mapandre.domain.services.UserService;
import com.mapandre.domain.services.UserServiceImpl;
import com.mapandre.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        this.repository = mock(UserRepository.class);
        this.service = new UserServiceImpl(repository);
    }

    @Test
    @DisplayName("Should return a valid user when the external id is valid")
    void ShouldReturnValidUserWhenExternalIdIsValid() {
        User expectedUser = new User(UUID.randomUUID(), "Ana", "Green",
                "548.550.280-61", "123456", LocalDate.of(2001, 12, 26));
        when(repository.findByExternalId(expectedUser.getExternalId())).thenReturn(Optional.of(expectedUser));

        User userResult = service.getById(expectedUser.getExternalId());

        assertEquals(userResult, expectedUser);
        verify(repository, times(1)).findByExternalId(expectedUser.getExternalId());
    }

    @Test
    @DisplayName("Should return a resource not found exception when the external id isn't valid")
    void ShouldReturnResourceNotFoundExceptionWhenExternalIdIsNotValid() {

        UUID random = UUID.randomUUID();

        when(repository.findByExternalId(random)).thenThrow(new ResourceNotFoundException("Resource not found by the informed id"));

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class,() -> service.getById(random));

        assertEquals("Resource not found by the informed id", exception.getMessage());
        verify(repository, times(1)).findByExternalId(random);
    }

    @Test
    @DisplayName("Should save a user in database when the given user is valid")
    void ShouldSaveUserInDatabaseWhenTheGivenUserIsValid() {
        User result = null;
        UserRequestDto requestDto = new UserRequestDto("Ana", "Green",
                "548.550.280-61", "123456", "26-12-2001");
        User userExpected = requestDto.convertToModel();
        when(repository.save(any())).thenReturn(userExpected);

        try {
           result = service.saveUser(requestDto);
        } catch (Exception e){
            fail("Shouldn't throw a exception");
        }
        assertEquals(result.getExternalId(), userExpected.getExternalId());
        assertEquals(result.getFirstName(), userExpected.getFirstName());
        assertEquals(result.getLastName(), userExpected.getLastName());
        assertEquals(result.getCpf(), userExpected.getCpf());
        assertEquals(result.getPassword(), userExpected.getPassword());
        assertEquals(result.getBirthDate(), userExpected.getBirthDate());
    }
}
