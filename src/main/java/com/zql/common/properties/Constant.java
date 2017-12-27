package com.zql.common.properties;

/**
 * 常量配置项，供静态工具类或者只能加载常量的注解读取的配置
 * Created by Administrator on 2017/11/3.
 */
public class Constant {

    public static final String AUTH_CACHE_PREFIX="auth";
    public static final String AUTH_ROLE_PREFIX="role:";
    public static final String AUTH_MENU_PREFIX="menu:";
    public static final String AUTH_EL_PREFIX="el:";
    public static final String AUTH_USER ="user";

    public static final Long SNOWFLAKE_WOKREDID= 1L;
    public static final Long SNOWFLAKE_DATACENTERID=1L;

    public static final Integer AUTHENTIATED=200;
    public static final Integer NOT_AUTHENTIATED=403;
    public static final Integer DENIED=401;
}
