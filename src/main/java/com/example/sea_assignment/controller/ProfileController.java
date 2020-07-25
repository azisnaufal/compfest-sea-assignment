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
            int userId = (int) session.getAttribute("userId");
            User user = userService.getById(userId);
            model.addAttribute(user);
            return "profile";
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/profile/update")
    public String update(@ModelAttribute("user") User user, Model model, HttpSession session){
        User emailExist = userService.findByEmail(user.getEmail());
        if (emailExist != null && emailExist.getId() != user.getId()){
            return "redirect:/profile?emailUsed=true";
        }
        else {
            userService.save(user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userAddress", user.getAddress());
            session.setAttribute("userName", user.getName());
            return "redirect:/profile?success=true";
        }
    }
}