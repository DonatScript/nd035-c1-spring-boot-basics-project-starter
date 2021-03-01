package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResource;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
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
    public String getFiles(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute("fileUpload") MultipartFile fileUpload,Model model){
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        this.fileService.deleteFile(id);
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        return "redirect:/home";
    }

    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, @RequestAttribute("fileUpload") MultipartFile fileUpload, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) throws IOException {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        if(fileUpload != null){
            if(!fileUpload.isEmpty()){
                if(this.fileService.findFileByName(fileUpload.getOriginalFilename()) == null){
                    FileResource file = this.readFileInputStream(fileUpload, userId);
                    Integer fileID = this.fileService.uploadFile(file);
                    file.setFileId(fileID);
                }else{
                    // file was uploaded
                }
            }
        }
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        return "redirect:/home";
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

//    @PostMapping("/file-upload")
//    public String handleFileUpload(@RequestAttribute("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
//        InputStream inputStream = fileUpload.getInputStream();
//
//        return inputStream.toString();
//    }

//    private void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
//
//        File file = new File(EXTERNAL_FILE_PATH + fileName);
//        if (file.exists()) {
//
//            //get the mimetype
//            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//            if (mimeType == null) {
//                //unknown mimetype so set the mimetype to application/octet-stream
//                mimeType = "application/octet-stream";
//            }
//
//            response.setContentType(mimeType);
//
//            /**
//             * In a regular HTTP response, the Content-Disposition response header is a
//             * header indicating if the content is expected to be displayed inline in the
//             * browser, that is, as a Web page or as part of a Web page, or as an
//             * attachment, that is downloaded and saved locally.
//             *
//             */
//
//            /**
//             * Here we have mentioned it to show inline
//             */
//            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//
//            //Here we have mentioned it to show as attachment
//            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
//
//            response.setContentLength((int) file.length());
//
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//            FileCopyUtils.copy(inputStream, response.);
//
//        }
//    }
}
