package org.mrchv.springbootstrap.controller;

import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.service.RoleService;
import org.mrchv.springbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAdminPage(@AuthenticationPrincipal UserDetails principal, ModelMap model) {
        model.addAttribute("principal", principal);
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("newUser", new User());

        return "admin-page";
    }

    @GetMapping("/update/{id}")
    public String setUpdateUserParams(@PathVariable("id") Long userId, ModelMap model) {
        model.addAttribute("editableUser", userService.findUserById(userId));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "fragments/edit-user-modal :: edit-user-modal";
    }

    @PostMapping("/add")
    public void addUser(@ModelAttribute("newUser") User newUser) {
        userService.addUser(newUser);
    }

}
