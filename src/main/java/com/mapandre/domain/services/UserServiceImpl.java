package com.mapandre.domain.services;

import com.mapandre.exceptions.ResourceNotFoundException;
import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;
import com.mapandre.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private static final String RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE = "Resource not found by the informed id";
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(UserRequestDto entity){
        return repository.save(entity.convertToModel());
    }

    @Override
    public List<User> getAll(){
        return repository.findAll();
    }

    @Override
    public User getById(UUID externalId){
        return repository.findByExternalId(externalId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public void deleteById(UUID externalId){
        User entity = repository.findByExternalId(externalId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
        repository.delete(entity);
    }

    @Override
    public User updateById(UUID externalId, UserRequestDto entity){
        return repository.findByExternalId(externalId).stream()
                .map(user -> user = updateData(entity, user.getExternalId()))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private User updateData(UserRequestDto requestDto, UUID externalId){
        return new User(externalId, requestDto.getFirstName(), requestDto.getLastName(), requestDto.getCpf(), requestDto.getPassword(), requestDto.getBirthDate());
    }
}
