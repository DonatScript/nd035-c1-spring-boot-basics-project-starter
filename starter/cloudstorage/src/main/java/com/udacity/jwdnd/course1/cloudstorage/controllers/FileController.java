package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @GetMapping
//    public String getFiles(@ModelAttribute("fileUpload") MultipartFile fileUpload,Model model){
//        model.addAttribute("files", this.fileService.getAllFiles());
//        return "home";
//    }
//    @PostMapping
//    public String handleFileUpload(@RequestAttribute("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
//        InputStream inputStream = fileUpload.getInputStream();
//
//        return inputStream.toString();
//    }
}
