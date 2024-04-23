package org.mrchv.springbootstrap.security;

import jakarta.annotation.PostConstruct;
import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
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

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        User admin = User.builder()
                .name("ivan")
                .lastName("ivanov")
                .age(5)
                .email("admin@mail.ru")
                .password("admin")
                .roles(Set.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER")))
                .build();

        userService.addUser(admin);


        User user = User.builder()
                .name("vladimir")
                .lastName("marychev")
                .age(6)
                .email("user@mail.ru")
                .password("user")
                .roles(Set.of(new Role("ROLE_USER")))
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
