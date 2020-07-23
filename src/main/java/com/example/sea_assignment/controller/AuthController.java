package com.example.sea_assignment.controller;

import com.example.sea_assignment.model.User;
import com.example.sea_assignment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        User user = null;
        if (model.getAttribute("user") == null){
            user = new User();
            model.addAttribute(user);
        }
        return "auth_register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model){
        User emailExist = userService.findByEmail(user.getEmail());
        if (emailExist == null){
            userService.store(user);
            model.addAttribute("success", true);
            return "redirect:/login";
        }
        else {
            return "redirect:/register?emailExist=true";
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            user.getId();
            return "redirect:/";
        }
        catch (NullPointerException e){
            User user = null;
            if (model.getAttribute("user") == null){
                user = new User();
                model.addAttribute(user);
            }
            return "auth_login";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpSession session){
        User emailExist = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (emailExist != null){
            session.setAttribute("user", emailExist);
            return "redirect:/";
        }
        else {
            return "redirect:/login?loginFailed=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user.getId() > 0){
                session.removeAttribute("user");
            }
            return "redirect:/";
        }
        catch (NullPointerException e){
            return "redirect:/";
        }

    }
}