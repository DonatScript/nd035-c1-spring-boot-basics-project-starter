package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userid){
        return this.noteMapper.getAllNotes(userid);
    }

    public boolean findNoteById(Integer noteid){
        return this.noteMapper.findNoteById(noteid) != null;
    }

    public int createNote(Note note, Integer userId){
        return this.noteMapper.createNote(new Note(null, note.getNotetitle(), note.getNotedescription(), userId));
    }

    public int updateNote(Note note){
        return this.noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteid){
        return this.noteMapper.deleteFile(noteid);
    }
}
