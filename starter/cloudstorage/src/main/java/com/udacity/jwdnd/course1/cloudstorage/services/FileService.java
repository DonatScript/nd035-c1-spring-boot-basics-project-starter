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
    public File findFileByName(String fileName){
        return this.fileMapper.findFileByName(fileName);
    }
    public int updateFile(File file){
        return this.fileMapper.updateFile(file);
    }
    public int uploadFile(File file){
        return this.fileMapper.uploadFile(file);
    }

    public int deleteFile(Integer fileId){
        return this.fileMapper.deleteFile(fileId);
    }
}
