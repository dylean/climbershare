package com.anxi.climbershare.cache;

import com.anxi.climbershare.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:AnXi
 */
@Component
public class MyBatisRedisCacheTransfer {

    @Autowired
    public void setRedisCatheUtil(RedisCacheUtil redisCatheUtil){
        MyBatisRedisCache.setRedisCacheUtil(redisCatheUtil);
    }

}
