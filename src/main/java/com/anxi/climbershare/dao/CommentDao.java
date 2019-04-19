package com.anxi.climbershare.dao;

import com.anxi.climbershare.cache.MyBatisRedisCache;
import org.apache.ibatis.annotations.*;
import com.anxi.climbershare.pojo.Comment;

import java.util.List;

/**
 * @Author:AnXi
 */
@Mapper
@CacheNamespace(implementation = MyBatisRedisCache.class)
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "entityId",column = "entity_id"),
            @Result(property = "entityType",column = "entity_type"),
    })
    Comment getCommentById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "entityId",column = "entity_id"),
            @Result(property = "entityType",column = "entity_type"),
    })
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select",SELECT_FIELDS, " from ", TABLE_NAME, " order by id desc limit #{offset},#{limit}"})
    @Results({
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createdDate",column = "created_date"),
            @Result(property = "entityId",column = "entity_id"),
            @Result(property = "entityType",column = "entity_type"),
    })
    List<Comment> selectLatestComments(@Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);
}
