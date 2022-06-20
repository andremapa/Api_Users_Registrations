package com.mapandre.domain.services;

import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User saveUser(UserRequestDto entity);
    List<User> getAll();
    User getById(UUID externalId);
    void deleteById(UUID externalId);
    User updateById(UUID externalId, UserRequestDto entity);
}
