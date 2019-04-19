package com.anxi.climbershare.cache;

import com.anxi.climbershare.utils.RedisCacheUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:AnXi
 */
public class MyBatisRedisCache implements Cache {


    private static RedisCacheUtil redisCacheUtil;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final Logger logger = LoggerFactory.getLogger(MyBatisRedisCache.class);
    private String id;

    /**
     * 静态注入工具类
     * @param redisCacheUtil
     */
    public static void setRedisCacheUtil(RedisCacheUtil redisCacheUtil){
        MyBatisRedisCache.redisCacheUtil = redisCacheUtil;
    }

    public MyBatisRedisCache(final String id) {
      if (id == null) {
        throw new IllegalArgumentException("Cache instances require an ID");
       }
      logger.debug(">>>>>>初始化 id:" + id);
       this.id = id;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        logger.debug(">>>>>>putObejct key:"+key);

        redisCacheUtil.putCathe(key,value);

    }

    @Override
    public Object getObject(Object key) {

        logger.debug("<<<<<<<<getObject key:"+key);
        Object value = redisCacheUtil.getCathe(key);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        logger.debug(">>>>>>>>>removeObject key:"+key);
        return redisCacheUtil.removeCathe(key);
    }

    @Override
    public void clear() {
        logger.debug(">>>>>>>>>清空缓存");
        redisCacheUtil.clearCathe();
    }

    @Override
    public int getSize() {
        return  redisCacheUtil.getCatheSize();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
