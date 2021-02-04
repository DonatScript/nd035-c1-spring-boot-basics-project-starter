package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("fileUpload") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model) {
        model.addAttribute("files", this.fileService.getAllFiles());
        model.addAttribute("notes", this.noteService.getAllNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        return "home";
    }

    @PostMapping("/home")
    public String addContent(@ModelAttribute("fileUpload") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model) {
        if(note.getNotetitle() != null && note.getNotedescription() != null){
            this.noteService.createNote(note);
        }else if(credential.getUrl() != null && credential.getUsername() != null && credential.getPassword() != null){
            this.credentialService.addCredential(credential);
        }
        return "home";
    }
//
//    @PostMapping("/logout")
//    public String logout(@RequestParam(value = "logout", required = false) String logout, Model model){
//        System.out.println("\n\nLogout\n\n");
//        model.addAttribute("param.logout",logout);
//        return "login";
//    }
//    @PostMapping("/file-upload")
//    public String handleFileUpload(@RequestAttribute("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
//        InputStream inputStream = fileUpload.getInputStream();
//
//        return inputStream.toString();
//    }
}
