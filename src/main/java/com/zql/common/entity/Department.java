package com.zql.common.entity;

/**
 * Created by Administrator on 2017/11/1.
 */
public class Department extends TreeEntity<Department,Department> {

    private static final long serialVersionUID = 1L;

    private String departName;
    private String departCode;
    private String departType;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public String getDepartType() {
        return departType;
    }

    public void setDepartType(String departType) {
        this.departType = departType;
    }

}
