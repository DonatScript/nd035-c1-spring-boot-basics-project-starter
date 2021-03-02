package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Integer userId){
        List<Credential> list = this.credentialMapper.getAllCredentials(userId);
        for (Credential credential: list) {
            var passwordDecrypted = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(passwordDecrypted);
        }
        return list;
    }

    public boolean findCredentialById(Integer credenialid){
        return this.credentialMapper.findCredentailById(credenialid) != null;
    }
    public int addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        return this.credentialMapper.addCredential(credential);
    }

    public int updateCredential(Credential credential){
        Credential cred = this.credentialMapper.findCredentailById(credential.getCredentialid());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), cred.getKey());
        credential.setPassword(encryptedPassword);
        return this.credentialMapper.updateCredentail(credential);
    }

    public int deleteCredential(Integer credentialid){
        return this.credentialMapper.deleteCredential(credentialid);
    }
}
