package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class NoteService {
    public final NoteMapper noteMapper;
    public final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public ArrayList<Note> getNotes(String username) {
        log.info("note service getNotes");
        User user = userMapper.getUser(username);
        ArrayList<Note> notes = noteMapper.getNotes(user.getUserid());
        log.debug(notes.toString());
        return notes;
    }

    public Note getNoteByTitle(String username, String noteTitle) {
        log.info("note service getNoteByTitle");
        User user = userMapper.getUser(username);
        return noteMapper.getNotesByTitle(user.getUserid(), noteTitle);
    }

    public int createNote(Note note, String username) {
        log.info("note service createNote");
        User user = userMapper.getUser(username);
        return noteMapper.insert(new Note(null, note.getNotetitle(), note.getNotedescription(), user.getUserid()));
    }

    public boolean updateNote(Note note, String username) {
        log.info("note service updateNote");
        User user = userMapper.getUser(username);
        return noteMapper.update(new Note(note.getNoteid(), note.getNotetitle(), note.getNotedescription(), user.getUserid()));
    }

    public boolean removeNote(Note note, String username) {
        log.info("note service removeNote");
        User user = userMapper.getUser(username);
        return noteMapper.delete(user.getUserid(), note.getNoteid());
    }
}
