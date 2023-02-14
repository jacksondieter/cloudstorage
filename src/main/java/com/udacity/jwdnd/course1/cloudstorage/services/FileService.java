package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileObj;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper,UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public ArrayList<Map<String,Object>> getFilenames(String username){
        User user = userMapper.getUser(username);
        ArrayList<Map<String,Object>> fileList= fileMapper.getFileNames(user.getUserid());
        System.out.println(fileList);
        return fileList;
    }

    public int addFile(MultipartFile file, String username) throws IOException {
        User user = userMapper.getUser(username);
        System.out.println(file);
        FileObj fileObj = new FileObj();
        fileObj.setFilename(file.getOriginalFilename());
        fileObj.setContenttype(file.getContentType());
        fileObj.setFilesize(String.valueOf(file.getSize()));
        byte[] data = file.getBytes();
        fileObj.setFiledata(data);
        fileObj.setUserid(user.getUserid());
        return fileMapper.insert(fileObj);
    }

    public FileObj getFile(Integer fileId, String username){
        User user = userMapper.getUser(username);
        FileObj fileObj= fileMapper.getFile(fileId,user.getUserid());
        return fileObj;
    }

    public boolean removeFile(Integer fileId,String username){
        User user = userMapper.getUser(username);
        return fileMapper.delete(user.getUserid(),fileId);
    }
}
