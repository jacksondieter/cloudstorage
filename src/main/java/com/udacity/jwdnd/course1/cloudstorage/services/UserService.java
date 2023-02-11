package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public boolean isUsernameUnAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public String authenticateUser(String username, String password){
        System.out.println(userMapper.getUsers());
        User user = userMapper.getUser(username);
        if(user == null) return "ERROR_USER";
        String encodedSalt = user.getSalt();
        String hashedPassword = hashService.getHashedValue(password,encodedSalt);
        if (user.getPassword().equals(hashedPassword)){
            return "AUTH_VALID";
        }
        return "ERROR_PASS";
    }

    public int createUser(User user){
        SecureRandom random= new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);
        return userMapper.insert(new User(null,user.getUsername(),hashedPassword,user.getFirstname(),user.getLastname(),encodedSalt));
    }
}