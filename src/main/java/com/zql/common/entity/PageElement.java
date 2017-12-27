package com.zql.common.entity;

/**
 * Created by Administrator on 2017/11/1.
 */
public class PageElement extends TreeEntity<PageElement,PageElement> {
    private static final long serialVersionUID = 1L;

    private Long menuId;
    private String elName;
    private String authCode;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getElName() {
        return elName;
    }

    public void setElName(String elName) {
        this.elName = elName;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
