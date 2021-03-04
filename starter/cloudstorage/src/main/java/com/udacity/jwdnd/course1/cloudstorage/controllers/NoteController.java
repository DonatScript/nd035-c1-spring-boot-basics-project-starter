package com.udacity.jwdnd.course1.cloudstorage.controllers;

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
    public String deleteNote(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute Note note, Model model){
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        this.noteService.deleteNote(id);
        model.addAttribute("resultStatus", "Success");
        model.addAttribute("resultMessage", "Note has been deleted");
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "result";
    }

    @PostMapping("/create")
    public String addNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        note.setUserid(userId);
        if(note.getNoteid() != null && this.noteService.findNoteById(note.getNoteid())){
            this.noteService.updateNote(note);
            model.addAttribute("resultMessage", "Note has been updated");
        }else {
            Integer noteID = this.noteService.createNote(note);
            note.setNoteid(noteID);
            model.addAttribute("resultMessage", "Note has been created");
        }

        model.addAttribute("resultStatus", "Success");
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        return "result";
    }

}
