package com.mapandre.domain.services;

import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;

import java.util.List;

public interface UserService {
    User post(UserRequestDto entity);
    List<User> getAll();
    User getById(long externalId);
    void deleteById(long externalId);
    User putById(long externalId, UserRequestDto entity);
}
