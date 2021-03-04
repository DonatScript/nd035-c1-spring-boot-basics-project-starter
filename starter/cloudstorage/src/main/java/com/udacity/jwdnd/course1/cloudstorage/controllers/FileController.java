package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileResource;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public String getFiles(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute("fileUpload") MultipartFile fileUpload, Model model){
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        this.fileService.deleteFile(id);
        model.addAttribute("resultStatus", "Success");
        model.addAttribute("resultMessage", "File has been deleted");
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        return "result";
    }

    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, @RequestAttribute("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        if(!fileUpload.isEmpty()){
            if(this.fileService.findFileByName(fileUpload.getOriginalFilename()) == null){
                FileResource file = this.readFileInputStream(fileUpload, userId);
                Integer fileID = this.fileService.uploadFile(file);
                file.setFileId(fileID);
                model.addAttribute("resultMessage", "File has been created.");
                model.addAttribute("resultStatus", "Success");
            }else{
                model.addAttribute("resultMessage", "File was uploaded.");
                model.addAttribute("resultStatus", "Error");
            }
        }else{
            model.addAttribute("resultStatus", "Error");
            model.addAttribute("resultMessage", "Something was wrong in file, it's not uploaded.");
        }

        model.addAttribute("files", this.fileService.getAllFiles(userId));
        return "result";
    }

    private FileResource readFileInputStream(MultipartFile fileUpload, Integer userId) throws IOException {
        FileResource file = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("FilesUploaded/"+fileUpload.getOriginalFilename());
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);

            // Writing to the file using ObjectOutputStream
            output.write(fileUpload.getBytes());

            FileInputStream fileStream = new FileInputStream("FilesUploaded/"+fileUpload.getOriginalFilename());
            // Creating an object input stream
            ObjectInputStream objStream = new ObjectInputStream(fileStream);

            file = new FileResource(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), objStream.available(), userId, objStream.readAllBytes());

            output.close();
            objStream.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
        return file;
    }
}
