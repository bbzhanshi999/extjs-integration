package com.zql.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zql.common.utils.IdGenrator;
import com.zql.common.utils.UserUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 基础entity，用户设置公共属性
 * Created by Administrator on 2017/11/1.
 */
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 删除标记（N：正常；Y：删除）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";

    public static final String YES = "1";
    public static final String NO = "0";


    public BaseEntity(){
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public BaseEntity(Long id) {
        this();
        this.id = id;
    }

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    protected boolean isNewRecord = false;


    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return
     */
    public boolean getIsNewRecord() {
        return isNewRecord || getId()==null;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert() {
        if (!this.isNewRecord) {
            setId(IdGenrator.snowFlakeId());
        }
        User user = UserUtils.getCurrentUser();
        if (user.getId()!=null) {
            this.creator = user;
        }
        this.updateTime = new Date();
        this.createTime = this.updateTime;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate() {
        this.updateTime = new Date();
        this.updater = UserUtils.getCurrentUser();
    }


    protected Long id;//id

    protected User creator;//创建人

    protected User updater;//最后修改人

    protected Date updateTime;//最后修改时间

    protected Date createTime;//创建时间

    protected String delFlag;//删除标记位

    protected Long createId;

    @JsonIgnore
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @JsonIgnore
    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    protected Long updateId;

    @JsonIgnore
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @JsonIgnore
    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    @JsonIgnore
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonIgnore
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public static void setCommonField(ResultSet rs,BaseEntity entity) throws SQLException {
        entity.setCreateTime(rs.getDate("create_time"));
        entity.setCreateId(rs.getLong("create_id"));
        entity.setUpdateTime(rs.getDate("update_time"));
        entity.setUpdateId(rs.getLong("update_id"));
        entity.setDelFlag(rs.getString("del_flag"));
        entity.setId(rs.getLong("id"));
    }
}
