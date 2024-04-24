package org.mrchv.springbootstrap.controller;

import org.mrchv.springbootstrap.model.Role;
import org.mrchv.springbootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPage(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        model.addAttribute("user", userDetails);
        model.addAttribute("users", userService.findAllUsers());

        model.addAttribute("roles", AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));
        return "admin-page";
    }

}
