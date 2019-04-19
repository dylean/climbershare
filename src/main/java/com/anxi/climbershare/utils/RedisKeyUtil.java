package com.anxi.climbershare.utils;

/**
 * 用来生成Redis的Key
 * @Author:AnXi
 */
public class RedisKeyUtil {
    private static String SPLIT = "_";
    private static String LIKE = "LIKE";
    private static String DISLIKE = "DISLIKE";
    private static String EVENTQUEUE = "EVENT_QUEUE";
    private static String EVENTDOING = "EVENT_DOING";

    public static String likeKey(int entityType,int entityId){
        return LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    public  static String dislikeKey(int entityType,int entityId){
        return DISLIKE+SPLIT+entityType+SPLIT+entityId;
    }

    public static  String eventQueueKey(){return EVENTQUEUE;}

    public static String eventDoingKey(){return  EVENTDOING;}

}
