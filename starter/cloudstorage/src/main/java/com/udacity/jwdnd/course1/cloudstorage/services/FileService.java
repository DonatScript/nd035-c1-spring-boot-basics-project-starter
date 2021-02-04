package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles(){
        return this.fileMapper.getAllFiles();
    }

    public int uploadFile(File file){
        return this.fileMapper.uploadFile(file);
    }

    public int deleteFile(File file){
        return this.fileMapper.deleteFile(file);
    }
}
