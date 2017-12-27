package com.zql.common.entity;

/**
 * Created by Administrator on 2017/11/1.
 */
public class Group extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
