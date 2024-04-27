package org.mrchv.springbootstrap.controller;

import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.service.RoleService;
import org.mrchv.springbootstrap.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

        return "pages/admin-page";
    }

    @GetMapping("/update/{id}")
    public String showModalUpdate(@PathVariable("id") Long userId, ModelMap model) {
        model.addAttribute("editableUser", userService.findUserById(userId));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "fragments/update-user-modal";
    }

    @GetMapping("/delete/{id}")
    public String showModalDelete(@PathVariable("id") Long userId, ModelMap model) {
        model.addAttribute("deletableUser", userService.findUserById(userId));
        return "fragments/delete-user-modal";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User newUser) {
        userService.addUser(newUser);
        return "redirect:/";
    }

    @PutMapping("/update")
    public String updateUser(@ModelAttribute("editableUser") User user) {
        userService.updateUser(user);
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@ModelAttribute("deletableUser") User user) {
        userService.removeUserById(user.getId());
        return "redirect:/";
    }
}
