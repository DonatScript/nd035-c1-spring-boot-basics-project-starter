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
import java.util.Optional;

@Controller
@RequestMapping("/home")
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
    @GetMapping
    public String getHomePage(@RequestParam(required = false) String operation ,@RequestParam(required = false) String operationModel ,@RequestParam(required = false) Integer id ,@ModelAttribute("file") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model) {
        Optional<String> opt = Optional.ofNullable(operation);
        if(opt.isPresent()){
            if(operation.equals("delete")){
                if(operationModel.equals("file")){
                    this.fileService.deleteFile(id);
                }else if(operationModel.equals("note")){
                    this.noteService.deleteNote(id);
                }else if(operationModel.equals("credential")){
                    this.credentialService.deleteCredential(id);
                }
            }
        }
        model.addAttribute("files", this.fileService.getAllFiles());
        model.addAttribute("notes", this.noteService.getAllNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        return "home";
    }

    @PostMapping
    public String addContent(@ModelAttribute("file") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model) {
        if(note.getNotetitle() != null && note.getNotedescription() != null){
            if(note.getNoteid() != null && this.noteService.findNoteById(note.getNoteid()).getNoteid().equals(note.getNoteid())){
                this.noteService.updateNote(note);
            }else {
                Integer noteID = this.noteService.createNote(note);
                note.setNoteid(noteID);
            }
        }else if(credential.getUrl() != null && credential.getUsername() != null && credential.getPassword() != null){
            if(credential.getCredentialid() != null && this.credentialService.findCredentialById(credential.getCredentialid()).getCredentialid().equals(credential.getCredentialid())){
                this.credentialService.updateCredential(credential);
            }else{
                Integer credentialID = this.credentialService.addCredential(credential);
                credential.setCredentialid(credentialID);
            }
        }
        model.addAttribute("files", this.fileService.getAllFiles());
        model.addAttribute("notes", this.noteService.getAllNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
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
