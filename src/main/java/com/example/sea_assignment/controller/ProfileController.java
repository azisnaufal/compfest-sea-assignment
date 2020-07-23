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
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String show(Model model, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            user.getId();
            model.addAttribute(user);
            return "profile";
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/profile/update")
    public String update(@ModelAttribute("user") User user, Model model){
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
}