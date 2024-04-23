package org.mrchv.springbootstrap.service;

import java.util.List;
import org.mrchv.springbootstrap.model.User;

public interface UserService {
    List<User> findAllUsers();
    User findUserById(Long id);
    User findUserByUsername(String username);
    void addUser(User user);
    void updateUser(User user);
    void removeUserById(Long id);
}
