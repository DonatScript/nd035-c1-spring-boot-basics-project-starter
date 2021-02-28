package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileResource;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FileResource> getAllFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    FileResource findFileByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(FileResource file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
