package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

//    @GetMapping("/home")
//    public String getNotes(@ModelAttribute("fileUpload") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model){
//        model.addAttribute("notes", this.noteService.getAllNotes());
//        return "home";
//    }
//    @PostMapping("/note")
//    public String addNote(@ModelAttribute("note") Note note, Model model) {
//        this.noteService.createNote(note);
//        return "home";
//    }
}
