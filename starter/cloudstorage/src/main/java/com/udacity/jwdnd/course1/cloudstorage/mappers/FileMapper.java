package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File findFileByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Update("UPDATE FIELS SET file = #{file}, contenttype = #{contenttype}, filesize = #{filesize}, userid = #{userid}, filedata = #{filedata} WHERE fileId = #{fileId}")
    int updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
