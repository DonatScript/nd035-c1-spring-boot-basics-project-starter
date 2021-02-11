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

    public List<Note> getAllNotes(){
        return this.noteMapper.getAllNotes();
    }

    public Note findNoteById(Integer noteid){
        return this.noteMapper.findNoteById(noteid);
    }

    public int createNote(Note note){
        return this.noteMapper.createNote(note);
    }

    public int updateNote(Note note){
        return this.noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteid){
        return this.noteMapper.deleteFile(noteid);
    }
}
