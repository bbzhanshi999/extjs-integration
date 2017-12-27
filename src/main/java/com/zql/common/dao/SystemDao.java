package com.zql.common.dao;

import com.google.common.collect.Lists;
import com.zql.common.entity.*;
import com.zql.common.properties.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统dao
 * Created by Administrator on 2017/11/2.
 */
@Repository
public class SystemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RedisTemplate redisTemplate;
    private final AuthEntityDao authEntityDao;

    @Autowired
    public SystemDao(NamedParameterJdbcTemplate jdbcTemplate, RedisTemplate redisTemplate, AuthEntityDao authEntityDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
        this.authEntityDao = authEntityDao;
    }

    @Cacheable(cacheNames = Constant.AUTH_CACHE_PREFIX,key="T(com.zql.common.properties.Constant).AUTH_ROLE_PREFIX+#authCode")
    public Role getRoleByAuth(String authCode){
        Role role =  jdbcTemplate.getJdbcOperations().queryForObject("select * from sys_role where auth_code=? and del_flag!=1", new RowMappers.RoleMapper(), authCode);

        List<Map<String, Object>> menuList = jdbcTemplate.getJdbcOperations().queryForList("select a.auth_code from sys_menu a,sys_role_menu b where b.role_id = ? AND " +
                "b.menu_id = a.id and a.del_flag!=1", role.getId());
        List<Map<String, Object>> elList = jdbcTemplate.getJdbcOperations().queryForList("select a.auth_code from sys_page_element a,sys_role_page_element b WHERE " +
                "a.id = b.el_id and b.role_id = ? and a.del_flag!=1", role.getId());
        List<Menu> menus = Lists.newArrayList();
        List<PageElement> pageEls = Lists.newArrayList();
        menuList.forEach(menuMap->{
            Menu menu = authEntityDao.getMenuByAuth((String) menuMap.get("auth_code"));
            menus.add(menu);
        });
        elList.forEach(elMap->{
            PageElement el = authEntityDao.getPageElementByAuth((String) elMap.get("auth_code"));
            pageEls.add(el);
        });
        role.setPageElements(pageEls);
        role.setMenus(menus);
        List<String> menuAuth = menus.stream().map(Menu::getAuthCode).collect(Collectors.toList());
        List<String> elAuth = pageEls.stream().map(PageElement::getAuthCode).collect(Collectors.toList());
        role.setAuthorities(Stream.of(menuAuth,elAuth).flatMap(Collection::stream).collect(Collectors.toList()));
        return role;
    }

    @Cacheable(cacheNames = Constant.AUTH_USER,key="#username")
    public User findUserByUsername(String username) {
        return jdbcTemplate.getJdbcOperations().queryForObject("SELECT * FROM sys_user WHERE username=? AND del_flag!='1'", new RowMappers.UserMapper(),username);
    }

    @CacheEvict(cacheNames = Constant.AUTH_CACHE_PREFIX,allEntries = true)
    public void clearAuthCache(){}

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 获得user的departments
     * @param userId
     * @return
     */
    public List<Department> findDepartmentsByUserId(Long userId){
        return jdbcTemplate.getJdbcOperations().query("SELECT b.* from sys_user_department a,sys_department b " +
                "where a.user_id = ? and b.id = a.depart_id and b.del_flag!=1",new RowMappers.DepartmentMapper(),userId);
    }

    /**
     * 获得user的posts
     * @param userId
     * @return
     */
    public List<Post> finaPostsByUserId(Long userId){
        return jdbcTemplate.getJdbcOperations().query("SELECT b.* from sys_user_post a,sys_post b " +
                "where a.user_id = ? and b.id = a.post_id and b.del_flag!=1",new RowMappers.PostMapper(),userId);
    }

    /**
     * 获得user的groups
     * @param userId
     * @return
     */
    public List<Group> findGroupsByUserId(Long userId){
        return jdbcTemplate.getJdbcOperations().query("SELECT b.* from sys_user_group a,sys_group b " +
                "where a.user_id = ? and b.id = a.group_id and b.del_flag!=1",new RowMappers.GroupMapper(),userId);
    }




}
