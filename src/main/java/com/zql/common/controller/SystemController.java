package com.zql.common.controller;

import com.zql.common.entity.User;
import com.zql.common.utils.UserUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/10/31.
 */
@RestController
@RequestMapping("/sys")
public class SystemController extends BaseController{

    @RequestMapping("userInfo")
    public User userInfo(){
        return UserUtils.getCurrentUser();
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("menuInfos")
    public String menuInfos(){
       //UserUtils.getAuthorities()
        return "success";
    }


}
