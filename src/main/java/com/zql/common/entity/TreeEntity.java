package com.zql.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.access.method.P;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/11/3.
 */
public  class TreeEntity<T,C> extends BaseEntity {
    private static final long serialVersionUID = 1L;

    protected T parent;    // 父级节点
    protected String pids; // 所有父级编号
    protected Long pid;

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public static void setTreeField(ResultSet rs, TreeEntity treeEntity) throws SQLException {
        treeEntity.setPid(rs.getLong("pid"));
        treeEntity.setPids(rs.getString("pids"));
    }
}
