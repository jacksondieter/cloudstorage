package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    ArrayList<Credential> getCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES(#{url}, #{username}, #{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username} , password = #{password} , key = #{key} WHERE userid = #{userid} AND credentialid = #{credentialid}")
    boolean update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialid}")
    boolean delete(Integer userid,Integer credentialid);
}
