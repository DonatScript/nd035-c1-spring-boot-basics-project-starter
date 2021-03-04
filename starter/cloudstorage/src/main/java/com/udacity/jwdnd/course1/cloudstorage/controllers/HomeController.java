package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResource;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private AuthenticationService authenticationService;


    public HomeController(AuthenticationService authenticationService, UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/home")
    public String renderHomePage(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute FileResource file, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "home";
    }

}
