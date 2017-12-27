package com.zql.common.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 集群session配置
 * Created by Administrator on 2017/11/2.
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=6000)//配置session失效时间10分钟
public class HttpSessionConfig {
}
