package com.agosh.account.user.servivce;

import com.agosh.account.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User updateUser(User user, Long id);
    void deleteUser(Long id);
    Optional<User> findUser(Long id);
}
