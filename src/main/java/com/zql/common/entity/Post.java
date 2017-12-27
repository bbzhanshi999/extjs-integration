package com.zql.common.entity;

/**
 * 岗位
 * Created by Administrator on 2017/11/1.
 */
public class Post extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String postName;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

}
