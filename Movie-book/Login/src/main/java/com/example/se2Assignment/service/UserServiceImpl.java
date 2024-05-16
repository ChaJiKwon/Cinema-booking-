package com.example.se2Assignment.service;

import com.example.se2Assignment.dto.UserDto;
import com.example.se2Assignment.model.User;
import com.example.se2Assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        String role = (userDto.getRole() != null) ? userDto.getRole() : "USER";
        User user = new User(userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()) , role, userDto.getFullname());
        return userRepository.save(user);
    }
}
