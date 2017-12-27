package com.zql.common.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 主键生成器
 * Created by Administrator on 2017/11/1.
 */
@Service
@Lazy(value = false)
public class IdGenrator {

    private static SecureRandom random = new SecureRandom();

    public static void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
        IdGenrator.snowflakeIdWorker = snowflakeIdWorker;
    }

    private static SnowflakeIdWorker snowflakeIdWorker;



    /**
     * 生成snowFlakeId
     * @return
     */
    public static long snowFlakeId(){
        return snowflakeIdWorker.nextId();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static  String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static  long randomLong() {
        return Math.abs(random.nextLong());
    }
}
