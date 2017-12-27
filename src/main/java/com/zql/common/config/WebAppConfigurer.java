package com.zql.common.config;

import com.zql.common.utils.IdGenrator;
import com.zql.common.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 资源配置
 * Created by Administrator on 2017/10/24.
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    /**
     * 将资源映射到指定路径上，比如将classpath:/ext-dev/映射到/ext-dev/**上
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ext-dev/**").addResourceLocations("classpath:/ext-dev/");
        registry.addResourceHandler("/ext-prod/**").addResourceLocations("classpath:/ext-prod/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 此方法用于配置自定义rediscachemanager。
     */
   /* @Bean
    public RedisCacheManager redisCacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setExpires(...);

    }*/

    /**
     * 让主键生成器在类加载时就读取配置文件中的机器id和dataacenterid
     *
     * @return
     */
    @Bean
    @Lazy(false)
    public int idGenrator() {
        IdGenrator.setSnowflakeIdWorker(new SnowflakeIdWorker(Long.parseLong(env.getProperty("snowflake.workerId")),
                Long.parseLong(env.getProperty("snowflake.datacenterId"))));
        return 1;
    }

    /**
     * 缓存key生成策略
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };

    }


    /**
     * 配置RedisTemplate的序列化策略
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }
}
