package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.FileObj;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface FileMapper {
    @Results(value = {@Result(property = "fileId", column = "FILEID"),
            @Result(property = "filename", column = "FILENAME")}
    )
    @Select("SELECT fileId,filename FROM FILES WHERE userid = #{userid}")
    ArrayList<Map<String, Object>> getFileNames(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileObj fileObj);


    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND fileId = #{fileId} ")
    FileObj getFileByID(Integer userid, Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename} ")
    FileObj getFileByFilename(Integer userid, String filename);

    @Delete("DELETE FROM FILES WHERE userid = #{userid} AND fileId = #{fileId}")
    boolean delete(Integer userid, Integer fileId);
}
