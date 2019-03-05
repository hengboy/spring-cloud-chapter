package com.yuqiyu.chapter.springcloud.alibaba.nacos.config.namespace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-05 15:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigNameSpaceTest {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ConfigNameSpaceTest.class);
    /**
     * 配置信息
     */
    @Value(value = "${hengboy.name:}")
    private String name;

    @Test
    public void getConfig() {
        logger.info("获取配置信息：{}", name);
    }
}
