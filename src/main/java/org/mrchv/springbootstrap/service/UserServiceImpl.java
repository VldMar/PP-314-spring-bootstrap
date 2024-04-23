package org.mrchv.springbootstrap.service;

import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepo = userRepository;
        this.encoder = encoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByEmail(username);
    }

    @Override
    public void addUser(User user) {
        User userFromDB = findUserByUsername(user.getUsername());
        if (userFromDB != null) {
            throw new RuntimeException("Пользователь с username=%s уже существует!".formatted(user.getUsername()));
        }

        if(user.getRoles() == null) {
            user.setRoles(Set.of(new Role("ROLE_USER")));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void updateUser(User user) {
        User userFromDB = findUserById(user.getId());
        if (userFromDB == null) {
            throw new RuntimeException("Пользователь с username=%s не найден!".formatted(user.getUsername()));
        }

        if (user.getRoles() == null) {
            user.setRoles(userFromDB.getRoles());
        }

        String password = user.getPassword() == ""
                ?   userFromDB.getPassword()
                :   encoder.encode(user.getPassword());

        user.setPassword(password);
        userRepo.save(user);
    }

    @Override
    public void removeUserById(Long id) {
            userRepo.deleteById(id);
    }
}
