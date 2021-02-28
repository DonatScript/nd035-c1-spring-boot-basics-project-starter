package com.udacity.jwdnd.course1.cloudstorage.models;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements MultipartFile {

    private Integer fileId;
    private String filename;
    private String contenttype;
    private Integer filesize;
    private Integer userid;
    private  byte[] filedata;

    public FileResource(Integer fileId, String filename, String contenttype, Integer filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public String getContenttype() {
        return contenttype;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String getName() {
        return this.getOriginalFilename();
    }

    @Override
    public String getOriginalFilename() {
        return this.filename;
    }

    @Override
    public String getContentType() {
        return this.contenttype;
    }

    @Override
    public boolean isEmpty() {
        return this.filedata.length == 0;
    }

    @Override
    public long getSize() {
        return this.filedata.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.filedata;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(new File("FilesUploaded/"+this.filename));
    }

    @Override
    public void transferTo(java.io.File file) throws IOException, IllegalStateException {

    }
}
