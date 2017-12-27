package com.zql.common.entity;

import java.io.Serializable;

/**
 * 进行权限投票是的临时bean
 * Created by Administrator on 2017/11/6.
 */
public class RoleAuthStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String GRANTED = "1";
    public static final String REFUSE = "0";


    private String authCode;
    private String status;

    public RoleAuthStatus(String authCode, String status) {
        this.authCode = authCode;
        this.status = status;
    }

    public RoleAuthStatus() {
        super();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleAuthStatus authStatus1 = (RoleAuthStatus) o;
        return authCode.equals(authStatus1.getAuthCode())&&status.equals(authStatus1.getStatus());
    }

    @Override
    public int hashCode() {
        int result = authCode != null ? authCode.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
