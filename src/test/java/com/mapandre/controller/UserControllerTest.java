package com.mapandre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;
import com.mapandre.domain.models.errors.ResourceNotFoundError;
import com.mapandre.domain.services.UserServiceImpl;
import com.mapandre.exceptions.ResourceNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    private final String BASE_URL = "/api/v1/users";

    private final ArgumentCaptor<UserRequestDto> captor = ArgumentCaptor.forClass(UserRequestDto.class);

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Test
    void ShouldCreateNewUserAndReturnHttpStatus201() throws Exception {

        UserRequestDto request = createRequest();
        User expectedUser = request.convertToModel();
        String jsonUser = mapper.writeValueAsString(request);

        when(service.saveUser(any(UserRequestDto.class))).thenReturn(expectedUser);

        mockMvc.perform(
                post(BASE_URL + "/postUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(request.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(request.getLastName())))
                .andExpect(jsonPath("$.cpf", is(request.getCpf())))
                .andExpect(jsonPath("$.password", is(request.getPassword())))
                .andExpect(jsonPath("$.birthDate", is(request.getBirthDate().toString())));

        verify(service).saveUser(captor.capture());

        UserRequestDto userCaptor = captor.getValue();

        assertEquals(userCaptor.getFirstName(), expectedUser.getFirstName());
        assertEquals(userCaptor.getLastName(), expectedUser.getLastName());
        assertEquals(userCaptor.getCpf(), expectedUser.getCpf());
        assertEquals(userCaptor.getPassword(), expectedUser.getPassword());
        assertEquals(userCaptor.getBirthDate(), expectedUser.getBirthDate());

        verify(service, times(1)).saveUser(any(UserRequestDto.class));
    }

    @Test
    void ShouldFindAllUsersOnDatabaseHttpStatus200() throws Exception{
        List<User> data = List.of(createRequest().convertToModel());

        when(service.getAll()).thenReturn(data);

        mockMvc.perform(
                get(BASE_URL + "/getAllUsers").accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(service, times(1)).getAll();
    }

    @Test
    void ShouldFindAUserByExternalIdHttpStatus200() throws Exception{

        User user = createRequest().convertToModel();

        when(service.getById(user.getExternalId())).thenReturn(user);

        mockMvc.perform(
                get(BASE_URL + "/getUserById/" + user.getExternalId())
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.externalId", is(user.getExternalId().toString())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.cpf", is(user.getCpf())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.birthDate", is(user.getBirthDate().toString())));

        verify(service, times(1)).getById(user.getExternalId());
    }

    @Test
    void ShouldReturnHttpStatus404WhenTheGivenIdIsInvalid() throws Exception {
        UUID random = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Resource not found by the informed id")).when(service).getById(random);
        mockMvc.perform(
                get(BASE_URL + "/getUserById/" + random)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is(ResourceNotFoundException.class.getName())))
                .andExpect(jsonPath("$.httpStatus", is("NOT_FOUND")));
    }

    @Test
    void ShouldUpdateUserByTheGivenExternalIdAndReturnHttpStatus200() throws Exception {

        User oldUser = createRequest().convertToModel();
        UserRequestDto request = new UserRequestDto.Builder()
                .withFirstName("Filipe").withLastName("Blue")
                .withCpf("040.632.080-28").withPassword("123456")
                .withBirthDate("1999-07-26").build();
        User newUser = request.convertToModel();

        when(service.updateById(eq(oldUser.getExternalId()), any(UserRequestDto.class))).thenReturn(newUser);

        mockMvc.perform(
                put(BASE_URL + "/putUserById/" + oldUser.getExternalId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(request.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(request.getLastName())))
                .andExpect(jsonPath("$.cpf", is(request.getCpf())))
                .andExpect(jsonPath("$.password", is(request.getPassword())))
                .andExpect(jsonPath("$.birthDate", is(request.getBirthDate().toString())));

        verify(service).updateById(eq(oldUser.getExternalId()), captor.capture());

        UserRequestDto userCaptor = captor.getValue();

        assertEquals(userCaptor.getFirstName(), newUser.getFirstName());
        assertEquals(userCaptor.getLastName(), newUser.getLastName());
        assertEquals(userCaptor.getCpf(), newUser.getCpf());
        assertEquals(userCaptor.getPassword(), newUser.getPassword());
        assertEquals(userCaptor.getBirthDate(), newUser.getBirthDate());

        verify(service, times(1)).updateById(eq(oldUser.getExternalId()), any(UserRequestDto.class));
    }

    @Test
    void ShouldDeleteUserByTheGivenIdAndReturnHttpStatus200() throws Exception {

        User user = createRequest().convertToModel();
        doNothing().when(service).deleteById(user.getExternalId());
        mockMvc.perform(
                delete(BASE_URL + "/deleteUserById/" + user.getExternalId())
        )
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(user.getExternalId());
    }

    private UserRequestDto createRequest(){
        return new UserRequestDto.Builder()
                .withFirstName("Alex").withLastName("Green")
                .withCpf("988.677.210-70").withPassword("123456")
                .withBirthDate("1985-08-08").build();
    }
}
