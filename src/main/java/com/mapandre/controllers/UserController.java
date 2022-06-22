package com.mapandre.controllers;

import com.mapandre.domain.dto.request.UserRequestDto;
import com.mapandre.domain.dto.response.UserResponseDto;
import com.mapandre.domain.models.User;
import com.mapandre.domain.services.UserService;
import com.mapandre.domain.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll().stream()
                .map(UserResponseDto::new)
                .peek(userResponseDto -> userResponseDto.add(linkTo(methodOn(UserController.class)
                        .getUser(userResponseDto.getExternalId())).withSelfRel())).toList());
    }

    @GetMapping(path = "/getUserById/{externalId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID externalId){
        return ResponseEntity.ok(new UserResponseDto(userService.getById(externalId))
                .add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("Users List")));
    }

    @PostMapping(path = "/postUser")
    public ResponseEntity<UserResponseDto> postUser(@RequestBody @Valid UserRequestDto userRequestDto){
        User obj = userService.saveUser(userRequestDto);
        return new ResponseEntity<>(new UserResponseDto(obj), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deleteUserById/{externalId}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable UUID externalId){
        userService.deleteById(externalId);
        return  ResponseEntity.ok().build();
    }

    @PutMapping(path = "/putUserById/{externalId}")
    public ResponseEntity<UserResponseDto> putUser(@PathVariable UUID externalId,
                                                   @RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok(new UserResponseDto(userService.updateById(externalId, userRequestDto)));
    }
}
