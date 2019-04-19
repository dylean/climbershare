package com.anxi.climbershare.dao;

import com.anxi.climbershare.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author:AnXi
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";


    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int insertUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "headUrl",column = "head_url"),
    })
    User selectById(int id);


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    @Results({
            @Result(property = "headUrl",column = "head_url"),
    })
    User selectByName(String name);


    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
