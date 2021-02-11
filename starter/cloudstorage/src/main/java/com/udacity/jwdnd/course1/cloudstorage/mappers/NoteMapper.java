package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note findNoteById(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int createNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteFile(Integer noteid);
}
