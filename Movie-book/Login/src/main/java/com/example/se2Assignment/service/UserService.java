package com.example.se2Assignment.service;

import com.example.se2Assignment.dto.UserDto;
import com.example.se2Assignment.model.User;

public interface UserService {
    User save (UserDto userDto);
}
