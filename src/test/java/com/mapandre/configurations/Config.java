package com.mapandre.configurations;

import com.mapandre.domain.models.User;
import com.mapandre.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Configuration()
public class Config implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(UUID.randomUUID(), "Ana", "Green",
                "461.615.190-38", "123456", LocalDate.of(1998, 8, 16));
        User u2 = new User(UUID.randomUUID(), "Marcus", "Brown",
                "189.670.220-18", "brown123", LocalDate.of(1984, 7, 30));
        User u3 = new User(UUID.randomUUID(), "Andrew", "Gray",
                "627.337.460-06", "gray456", LocalDate.of(1999, 12, 8));

        repository.saveAll(Arrays.asList(u1, u2, u3));
    }
}
