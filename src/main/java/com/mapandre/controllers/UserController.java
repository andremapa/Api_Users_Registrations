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

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll().stream().map(UserResponseDto::new).toList());
    }

    @GetMapping(path = "/getUserById/{externalId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable long externalId){
        return ResponseEntity.ok(new UserResponseDto(userService.getById(externalId)));
    }

    @PostMapping(path = "/postUser")
    public ResponseEntity<UserResponseDto> postUser(@RequestBody @Valid UserRequestDto userRequestDto){
        User obj = userService.post(userRequestDto);
        return new ResponseEntity<>(new UserResponseDto(obj), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deleteUserById/{externalId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long externalId){
        userService.deleteById(externalId);
        return  ResponseEntity.ok().build();
    }

    @PutMapping(path = "/putUserById/{externalId}")
    public ResponseEntity<UserResponseDto> putUser(@PathVariable long externalId,
                                                   @RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok(new UserResponseDto(userService.putById(externalId, userRequestDto)));
    }
}
