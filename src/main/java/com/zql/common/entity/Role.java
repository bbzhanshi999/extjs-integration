package com.zql.common.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String authCode;
    private String roleName;
    private String decription;
    private List<Menu> menus;
    private List<String> authorities;
    public List<PageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    private List<PageElement> pageElements;



    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }




    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
