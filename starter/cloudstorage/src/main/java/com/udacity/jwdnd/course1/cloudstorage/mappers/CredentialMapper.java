package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential findCredentailById(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    int updateCredentail(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredential(Integer credentialid);
}
