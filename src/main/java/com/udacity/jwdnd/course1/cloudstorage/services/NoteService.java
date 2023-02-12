package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    public final NoteMapper noteMapper;
    public final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper,UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public ArrayList<Note> getNotes(String username){
        User user = userMapper.getUser(username);
        return noteMapper.getNotes(user.getUserid());
    }

    public int createNote(Note note,String username){
        User user = userMapper.getUser(username);
        return noteMapper.insert(new Note(null,note.getNotetitle(),note.getNotedescription(),user.getUserid()));
    }

    public boolean updateNote(Note note,String username){
        User user = userMapper.getUser(username);
        return noteMapper.update(new Note(note.getNoteid(),note.getNotetitle(),note.getNotedescription(),user.getUserid()));
    }

    public boolean removeNote(Note note,String username){
        User user = userMapper.getUser(username);
        return noteMapper.delete(user.getUserid(),note.getNoteid());
    }
}
