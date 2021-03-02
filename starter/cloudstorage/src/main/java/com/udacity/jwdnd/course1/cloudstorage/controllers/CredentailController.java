package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/credential")
public class CredentailController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentailController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/delete")
    public String deleteCredential(Authentication authentication, @RequestParam(required = false) Integer id , @ModelAttribute Credential credential, Model model){
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        this.credentialService.deleteCredential(id);
        model.addAttribute("resultStatus", "Success");
        model.addAttribute("resultMessage", "Credential has been deleted");
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "result";
    }
    @PostMapping("/create")
    public String addCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        String username = authentication.getName();
        User user = this.userService.getUser(username);
        Integer userId = user.getUserId();
        credential.setUserid(userId);
        if(credential.getCredentialid() != null && this.credentialService.findCredentialById(credential.getCredentialid())){
            this.credentialService.updateCredential(credential);
            model.addAttribute("resultMessage", "Credential has been updated");
        }else{
            Integer credentialID = this.credentialService.addCredential(credential);
            credential.setCredentialid(credentialID);
            model.addAttribute("resultMessage", "Credential has been created");
        }
        model.addAttribute("resultStatus", "Success");
        model.addAttribute("credentials", this.credentialService.getAllCredentials(userId));
        return "result";
    }
}
