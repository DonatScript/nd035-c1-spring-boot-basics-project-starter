package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper){
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(Integer userId){
        return this.credentialMapper.getAllCredentials(userId);
    }

    public boolean findCredentialById(Integer credenialid){
        return this.credentialMapper.findCredentailById(credenialid) != null;
    }
    public int addCredential(Credential credential, Integer userId){
        return this.credentialMapper.addCredential(new Credential(credential.getCredentialid(),credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), userId));
    }

    public int updateCredential(Credential credential){
        return this.credentialMapper.updateCredentail(credential);
    }

    public int deleteCredential(Integer credentialid){
        return this.credentialMapper.deleteCredential(credentialid);
    }
}
