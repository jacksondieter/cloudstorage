package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileObj;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public ArrayList<Map<String, Object>> getFilenames(String username) {
        log.info("file service getFilenames");
        User user = userMapper.getUser(username);
        ArrayList<Map<String, Object>> fileList = fileMapper.getFileNames(user.getUserid());
        log.debug(fileList.toString());
        return fileList;
    }

    public int addFile(MultipartFile file, String username) throws IOException {
        log.info("file service addFile");
        log.debug(file.getOriginalFilename());
        User user = userMapper.getUser(username);
        FileObj fileObj = new FileObj();
        fileObj.setFilename(file.getOriginalFilename());
        fileObj.setContenttype(file.getContentType());
        fileObj.setFilesize(String.valueOf(file.getSize()));
        byte[] data = file.getBytes();
        fileObj.setFiledata(data);
        fileObj.setUserid(user.getUserid());
        return fileMapper.insert(fileObj);
    }

    public FileObj getFileById(Integer fileId, String username) {
        User user = userMapper.getUser(username);
        return fileMapper.getFileByID(user.getUserid(), fileId);
    }

    public FileObj getFileByFilename(String filename, String username) {
        User user = userMapper.getUser(username);
        return fileMapper.getFileByFilename(user.getUserid(), filename);
    }

    public boolean removeFile(Integer fileId, String username) {
        User user = userMapper.getUser(username);
        return fileMapper.delete(user.getUserid(), fileId);
    }
}
