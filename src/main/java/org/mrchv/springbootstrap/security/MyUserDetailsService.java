package org.mrchv.springbootstrap.security;

import jakarta.annotation.PostConstruct;
import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.service.RoleService;
import org.mrchv.springbootstrap.service.RoleServiceImpl;
import org.mrchv.springbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MyUserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        User admin = User.builder()
                .name("ivan")
                .lastName("ivanov")
                .age(5)
                .email("admin@mail.ru")
                .password("admin")
                .roles(Set.of(adminRole, userRole))
                .build();

        userService.addUser(admin);

        User user = User.builder()
                .name("vladimir")
                .lastName("marychev")
                .age(6)
                .email("user@mail.ru")
                .password("user")
                .roles(Set.of(userRole))
                .build();

        userService.addUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь %s не найден".formatted(username));
        }

        return user;
    }
}
