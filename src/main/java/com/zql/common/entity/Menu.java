package com.zql.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * 菜单
 * Created by Administrator on 2017/11/1.
 */
public class Menu extends TreeEntity<Menu,Menu>{

    private static final long serialVersionUID = 1L;

    private String menuName;
    private String authCode;
    private String url;
    private int sort;
    private String iconCls;
    private String type;

    private static final String LV1="0";
    private static final String LV2="1";
    private static final String LV3="2";
    private static final String LV4="3";

    public List<PageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    private List<PageElement> pageElements;


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }


    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, Long parentId, boolean cascade){
        // TODO: 2017/11/13
    }
}
