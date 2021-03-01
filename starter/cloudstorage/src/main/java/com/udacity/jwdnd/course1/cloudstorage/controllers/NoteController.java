package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public String deleteNote(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute Note note, @ModelAttribute Credential credential, Model model){
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        this.noteService.deleteNote(id);
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "redirect:/result";
    }

    @PostMapping("/create")
    public String addNote(Authentication authentication, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        if(note.getNoteid() != null && this.noteService.findNoteById(note.getNoteid())){
            this.noteService.updateNote(note);
        }else {
            Integer noteID = this.noteService.createNote(note, user.getUserId());
            note.setNoteid(noteID);
        }

        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("resultStatus", "Success");
        model.addAttribute("resultM" +
                "essage", "Note has been created");
        return "redirect:/result";
    }

}
