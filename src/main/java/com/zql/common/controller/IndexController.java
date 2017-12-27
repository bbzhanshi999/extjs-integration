package com.zql.common.controller;

import com.zql.common.properties.Constant;
import com.zql.common.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by Administrator on 2017/10/18.
 */
@Controller
public class IndexController extends BaseController {


    @RequestMapping("/")
    public String login(HttpServletRequest request) {
        return isAjax(request)?"forward:/login/ajaxHandler":"forward:/login.jsp";
    }

    @ResponseBody
    @RequestMapping("/login/ajaxHandler")
    public String loginAjax(){
        return "{loginStatus:"+ Constant.NOT_AUTHENTIATED+"}";
    }

    @ResponseBody
    @RequestMapping("/loginFailure")
    public String loginFailure() {
        return "{status:0}";
    }


    @ResponseBody
    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "{status:1}";
    }

    @RequestMapping("/logoutSuccess")
    public String logout(HttpServletRequest req){
        return isAjax(req)?"forward:/logoutSuccess/ajaxHandler":"redirect:/";
    }

    @ResponseBody
    @RequestMapping("/logoutSuccess/ajaxHandler")
    public String logoutAjax(){
        return "{logout:1}";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied(HttpServletRequest req){
        return isAjax(req)?"forward:/accessDenied/ajaxHandler":"redirect:/";
    }

    @ResponseBody
    @RequestMapping("/accessDenied/ajaxHandler")
    public String accessDeniedAjax(HttpServletRequest req, HttpServletResponse resp){
        return "{accessDenied:1}";
    }


    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("user", UserUtils.getCurrentUser());
        return "index";
    }

    /**
     * 验证是否已经登录
     * @return
     */
    @ResponseBody
    @RequestMapping("/isAuthenticated")
    public String isAuthenticated() {
        return "{accessDenied:0}";
    }
}
