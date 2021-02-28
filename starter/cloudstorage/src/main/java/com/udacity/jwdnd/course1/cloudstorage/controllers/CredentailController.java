package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CredentailController {

    private CredentialService credentialService;

    public CredentailController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

//    @GetMapping("/home")
//    public String getCredentials(@ModelAttribute("fileUpload") File file, @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model){
//        model.addAttribute("credentials", this.credentialService.getAllCredentials());
//        return "home";
//    }
//    @PostMapping("/credential")
//    public String addCredential(@ModelAttribute("credential") Credential credential, Model model) {
//        this.credentialService.addCredential(credential);
//        return "home";
//    }
}
