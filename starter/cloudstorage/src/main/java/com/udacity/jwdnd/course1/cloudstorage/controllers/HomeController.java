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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@Controller
@RequestMapping("/home")
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

    @GetMapping
    public String renderHomePage(Authentication authentication, @RequestParam(required = false) String operation , @RequestParam(required = false) String operationModel , @RequestParam(required = false) Integer id , @ModelAttribute FileResource file, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
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
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "home";
    }

    @PostMapping
    public String addContent(Authentication authentication, @RequestAttribute("fileUpload") MultipartFile fileUpload, @ModelAttribute Note note, @ModelAttribute Credential credential, Model model) throws IOException {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        if(fileUpload != null){
            if(!fileUpload.isEmpty()){
                if(!this.fileService.findFileByName(fileUpload.getOriginalFilename())){
                    FileResource file = this.readFileInputStream(fileUpload, userId);
                    Integer fileID = this.fileService.uploadFile(file);
                    System.out.println(fileID+"\tfileID");
                    file.setFileId(fileID);
                }else{
                    // file was uploaded
                }
            }
        }else if(note.getNotetitle() != null && note.getNotedescription() != null){
            if(note.getNoteid() != null && this.noteService.findNoteById(note.getNoteid())){
                this.noteService.updateNote(note);
            }else {
                Integer noteID = this.noteService.createNote(note, user.getUserId());
                note.setNoteid(noteID);
            }
        }else if(credential.getUrl() != null && credential.getUsername() != null && credential.getPassword() != null){
            if(credential.getCredentialid() != null && this.credentialService.findCredentialById(credential.getCredentialid())){
                this.credentialService.updateCredential(credential);
            }else{
                Integer credentialID = this.credentialService.addCredential(credential, user.getUserId());
                credential.setCredentialid(credentialID);
            }
        }
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("notes", this.noteService.getAllNotes(userId));
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "home";
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
