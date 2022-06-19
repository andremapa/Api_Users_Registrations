package com.mapandre.domain.services;

import com.mapandre.exceptions.ResourceNotFoundException;
import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.models.User;
import com.mapandre.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static final String RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE = "Resource not found by the informed id";
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User post(UserRequestDto entity){
        return repository.save(convertRequest(entity));
    }

    @Override
    public List<User> getAll(){
        return repository.findAll();
    }

    @Override
    public User getById(long externalId){
        return repository.findByExternalId(externalId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public void deleteById(long externalId){
        User entity = repository.findByExternalId(externalId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
        repository.delete(entity);
    }

    @Override
    public User putById(long externalId, UserRequestDto entity){
        //TODO: MODIFY THE METHOD TO BE MORE PERFORMATIVE
        return repository.findAll().stream()
                .filter(user -> user.getExternalId() == externalId)
                .map(user -> user = updateData(entity, user.getExternalId()))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private User convertRequest(UserRequestDto requestDto){
        //TODO: MAKE A BETTER RULER TO GENERATE A EXTERNAL ID;
        return new User(repository.count(), requestDto.getFirstName(), requestDto.getLastName(), requestDto.getCpf(), requestDto.getPassword(), requestDto.getBirthDate());
    }

    private User updateData(UserRequestDto requestDto, long externalId){
        return new User(externalId, requestDto.getFirstName(), requestDto.getLastName(), requestDto.getCpf(), requestDto.getPassword(), requestDto.getBirthDate());

    }
}
