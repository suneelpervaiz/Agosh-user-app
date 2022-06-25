package com.agosh.account.user.controller;

import com.agosh.account.user.model.User;
import com.agosh.account.user.servivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    // Find
    @GetMapping("/users")
    List<User> findAll() {
        return userService.getAllUsers();
    }

    // Save
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    // update
    @PutMapping ("/users/{id}")
    ResponseEntity<User> updateUser(@Valid @PathVariable("id") long id,  @RequestBody User user) {


        return userService.findUser(id)
                .map(savedUser -> {
                    savedUser.setFirstName(user.getFirstName());
                    savedUser.setLastName(user.getLastName());
                    savedUser.setEmail(user.getEmail());

                    User updatedUser = userService.updateUser(user,id);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());


    }

    // Find
    @GetMapping("/users/{id}")
    ResponseEntity<User> findOne(@PathVariable Long id) {
        return userService.findUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }




}
