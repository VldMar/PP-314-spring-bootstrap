package org.mrchv.springbootstrap.controller;

import org.mrchv.springbootstrap.model.User;
import org.mrchv.springbootstrap.util.UserUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String showUserPage(@AuthenticationPrincipal UserDetails principal, ModelMap model) {
        model.addAttribute("user", (User) principal);
        model.addAttribute("isAdmin", UserUtil.isUserAdmin(principal));
        return "pages/user-page";
    }
}
