package com.zql.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * mybatis整合测试
 * Created by Administrator on 2017/12/14.
 */
@Repository
public interface BusinessMapper {

    public Integer testCount(String id);
}
