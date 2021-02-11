package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

//    @GetMapping("/get")
//    public String getNotes(@RequestParam(required = false) Integer id, @ModelAttribute("note") Note note, Model model){
//        this.noteService.deleteNote(id);
//        model.addAttribute("notes", this.noteService.getAllNotes());
//        return "home";
//    }
//    @GetMapping("/delete")
//    public String deleteNote(@RequestParam(required = false) Integer id, @ModelAttribute("note") Note note, Model model){
//        this.noteService.deleteNote(id);
//        model.addAttribute("notes", this.noteService.getAllNotes());
//        return "home";
//    }
//    @PostMapping
//    public String addNote(@ModelAttribute("note") Note note, Model model) {
//        this.noteService.createNote(note);
//        return "home";
//    }
}
