package com.zql.common.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/7.
 */
public class BaseController {

    protected boolean isAjax(HttpServletRequest req){
        return req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
    }
}
