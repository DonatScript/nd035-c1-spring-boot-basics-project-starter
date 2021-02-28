package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public List<FileResource> getAllFiles(Integer userId){
        return this.fileMapper.getAllFiles(userId);
    }
    public boolean findFileByName(String fileName){
        return this.fileMapper.findFileByName(fileName) != null;
    }
    public int uploadFile(FileResource file){
        return this.fileMapper.uploadFile(file);
    }

    public int deleteFile(Integer fileId){
        return this.fileMapper.deleteFile(fileId);
    }
}
