package com.mapandre.controller;

import com.mapandre.controllers.UserController;
import com.mapandre.domain.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class UserControllerTest {

    private UserServiceImpl service;
    private UserController userController;

    @BeforeEach
    void setUp() {
        this.service = mock(UserServiceImpl.class);
        this.userController = new UserController(service);
    }

    @Test
    void name() {
    }
}
