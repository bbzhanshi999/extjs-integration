package com.zql.common.service;

import com.zql.common.dao.SystemDao;
import com.zql.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统服务
 * Created by Administrator on 2017/11/6.
 */
@Service
public class SystemService {

    @Autowired
    private SystemDao systemDao;


    /**
     * 初始化权限缓存
     */
    public void cacheAuth() {
        List<Map<String, Object>> roleList = systemDao.getJdbcTemplate().getJdbcOperations().queryForList("SELECT auth_code FROM sys_role WHERE del_flag!=1");
        roleList.forEach(roleMap -> {
            Role role = systemDao.getRoleByAuth((String) roleMap.get("auth_code"));
        });
    }

    /**
     * 清空缓存
     */
    public void refreshAuthCache() {
        systemDao.clearAuthCache();
    }
}
