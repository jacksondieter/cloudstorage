package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CredentialService {
    public final CredentialMapper credentialMapper;
    public final UserMapper userMapper;
    public final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public ArrayList<Credential> getCredentials(String username) {
        log.info("credential service getCredentials");
        User user = userMapper.getUser(username);
        ArrayList<Credential> credentialArrayList = credentialMapper.getCredentials(user.getUserid());
        return new ArrayList<Credential>(credentialArrayList.stream().map(credential -> {
            String decodedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            return new Credential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), decodedPassword, credential.getPassword(), user.getUserid());
        }).collect(Collectors.toList()));
    }

    public int createCredential(Credential credential, String username) {
        log.info("credential service createCredential");
        User user = userMapper.getUser(username);
        //process values
        String encodedKey = encryptionService.createKey();
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedPassword, encodedKey, user.getUserid()));
    }

    public boolean updateCredential(Credential credential, String username) {
        log.info("credential service updateCredential");
        User user = userMapper.getUser(username);
        //process values
        String encodedKey = encryptionService.createKey();
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.update(new Credential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), encodedPassword, encodedKey, user.getUserid()));
    }

    public boolean removeCredential(Credential credential, String username) {
        log.info("credential service removeCredential");
        User user = userMapper.getUser(username);
        return credentialMapper.delete(user.getUserid(), credential.getCredentialid());
    }
}
