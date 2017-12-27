package com.zql.common.service.runner;

import com.zql.common.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 服务启动执行器
 * Created by Administrator on 2017/11/2.
 */
@Service
public class AppInitLoader implements ApplicationRunner {

    @Autowired
    private SystemService systemService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        cacheLoad();
    }


    /**
     * 启动加载缓存
     */
    private void cacheLoad(){
        systemService.cacheAuth();
    }
}
