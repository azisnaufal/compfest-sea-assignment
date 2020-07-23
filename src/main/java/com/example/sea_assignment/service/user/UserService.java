package com.example.sea_assignment.service.user;

import com.example.sea_assignment.model.User;

import java.util.List;

public interface UserService {
    List<User> get();
    void store(User user);
    User getById(Integer id);
    User findByEmailAndPassword(String email, String Password);
    User findByEmail(String email);
}
