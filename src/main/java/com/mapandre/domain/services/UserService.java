package com.mapandre.domain.services;

import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User post(UserRequestDto entity);
    List<User> getAll();
    User getById(UUID externalId);
    void deleteById(UUID externalId);
    User putById(UUID externalId, UserRequestDto entity);
}
