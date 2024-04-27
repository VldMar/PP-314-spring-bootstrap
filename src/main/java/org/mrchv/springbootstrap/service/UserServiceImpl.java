package org.mrchv.springbootstrap.service;

import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.repository.UserRepository;
import org.mrchv.springbootstrap.util.UserUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, RoleService roleService) {
        this.userRepo = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
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

        if (user.getRoles().size() == 0) {
            user.setRoles(Set.of(new Role("ROLE_USER")));
        } else if (UserUtil.isUserAdmin(user)) {
            user.setRoles(Set.copyOf(roleService.findAllRoles()));
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

        if (user.getRoles().size() == 0) {
            user.setRoles(userFromDB.getRoles());
        } else if (UserUtil.isUserAdmin(user)) {
            user.setRoles(Set.copyOf(roleService.findAllRoles()));
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
