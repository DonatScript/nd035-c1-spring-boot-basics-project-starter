package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper){
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(){
        return this.credentialMapper.getAllCredentials();
    }

    public Credential findCredentialById(Integer credenialid){
        return this.credentialMapper.findCredentailById(credenialid);
    }
    public int addCredential(Credential credential){
        return this.credentialMapper.addCredential(credential);
    }

    public int updateCredential(Credential credential){
        return this.credentialMapper.updateCredentail(credential);
    }

    public int deleteCredential(Integer credentialid){
        return this.credentialMapper.deleteCredential(credentialid);
    }
}
