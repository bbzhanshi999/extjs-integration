package com.zql.common.utils;

import com.zql.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * 用户及权限信息获取工具类
 * Created by Administrator on 2017/11/1.
 */
public class UserUtils {

    /**
     * 返回用户所有权限标识
     * @return
     */
    public static Collection<? extends GrantedAuthority> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    /**
     * 确认当前线程用户是否登录成功
     * @return
     */
    @Deprecated
    public static boolean isAuthenticated(){
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticated||principal==null||"anonymousUser".equals(principal)) {
            return false;
        }
        return true;
    }

    /**
     * 获得当前线程的用户
     * @return
     */
    public static User getCurrentUser() {
        User user;
        try{
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (ClassCastException e){
            return null;
        }
        return user;
    }

    //todo

}
