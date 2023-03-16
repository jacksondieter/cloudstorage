package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public User getUser(String username) {
        log.info("user service getUser");
        return userMapper.getUser(username);
    }

    public boolean isUsernameUnAvailable(String username) {
        log.info("user service isUsernameUnAvailable");
        return userMapper.getUser(username) == null;
    }

    public String authenticateUser(String username, String password) {
        log.info("user service authenticateUser");
        User user = userMapper.getUser(username);
        if (user == null) return "ERROR_USER";
        String encodedSalt = user.getSalt();
        String hashedPassword = hashService.getHashedValue(password, encodedSalt);
        if (user.getPassword().equals(hashedPassword)) {
            return "AUTH_VALID";
        }
        return "ERROR_PASS";
    }

    public int createUser(User user) {
        log.info("user service createUser");
        String encodedSalt = encryptionService.createKey();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), hashedPassword, user.getFirstname(), user.getLastname(), encodedSalt));
    }
}
