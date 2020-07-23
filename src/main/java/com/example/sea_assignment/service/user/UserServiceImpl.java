package com.example.sea_assignment.service.user;

import com.example.sea_assignment.model.User;
import com.example.sea_assignment.repository.UserRepository;
import com.example.sea_assignment.utils.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Encrypt encrypt;

    @Override
    public List<User> get() {
        return userRepository.findAll();
    }

    @Override
    public void store(User user) {
        user.setPassword(encrypt.getMd5(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }
        else {
            throw new RuntimeException("User not found!");
        }

        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String md5 = encrypt.getMd5(password);
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, md5);

        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }

        return user;
    }
}
