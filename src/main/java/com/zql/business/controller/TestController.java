package com.zql.business.controller;

import com.zql.business.dao.BusinessMapper;
import com.zql.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试路由
 * Created by Administrator on 2017/12/14.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private BusinessMapper mapper;

    @RequestMapping("testMethod1")
    public String testMethod1(){
        Integer integer = mapper.testCount("1");
        //Integer integer = 1;
        return "{result:"+integer+"}";
    }
}
