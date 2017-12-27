package com.zql.common.dao;

import com.zql.common.entity.Menu;
import com.zql.common.entity.PageElement;
import com.zql.common.properties.Constant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单及页面元素dao
 * Created by Administrator on 2017/11/6.
 */
@Repository
public class AuthEntityDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RedisTemplate redisTemplate;

    public AuthEntityDao(NamedParameterJdbcTemplate jdbcTemplate, RedisTemplate redisTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(cacheNames = Constant.AUTH_CACHE_PREFIX,key="T(com.zql.common.properties.Constant).AUTH_MENU_PREFIX+#authCode")
    public Menu getMenuByAuth(String authCode){
        return jdbcTemplate.getJdbcOperations().queryForObject("select * from sys_menu where auth_code=? and del_flag!=1",new RowMappers.MenuMapper(),authCode);
    }

    @Cacheable(cacheNames = Constant.AUTH_CACHE_PREFIX,key="T(com.zql.common.properties.Constant).AUTH_EL_PREFIX+#authCode")
    public PageElement getPageElementByAuth(String authCode){
        return jdbcTemplate.getJdbcOperations().queryForObject("select * from sys_page_element where auth_code=? and del_flag!=1",new RowMappers.PageElMapper(),authCode);
    }

    public List<Menu> findMenusByRole(Long roleId){
        return jdbcTemplate.getJdbcOperations().query("select a.auth_code from sys_menu a,sys_role_menu b where b.role_id = ? AND " +
                "b.menu_id = a.id and a.del_flag!=1",new RowMappers.MenuMapper(),roleId);
    }

    public List<PageElement> findPageElByRole(Long roleId){
        return jdbcTemplate.getJdbcOperations().query("select a.* from sys_page_element a,sys_role_page_element b WHERE " +
                "a.id = b.el_id and b.role_id = ? and a.del_flag!=1",new RowMappers.PageElMapper(),roleId);
    }
}
