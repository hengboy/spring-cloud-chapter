package com.yuqiyu.chapter.springcloud.alibaba.nacos.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringCloud Alibaba Nacos Config 内容变动监听
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-05 11:04
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@SpringBootApplication
public class SpringCloudAlibabaNacosConfigListenerApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SpringCloudAlibabaNacosConfigListenerApplication.class);

    /**
     * 配置信息对应的数据编号
     */
    static final String DATA_ID = "hengboy-spring-cloud-alibaba-nacos-config-listener.yaml";

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaNacosConfigListenerApplication.class, args);
    }

    /*@NacosConfigListener(dataId = DATA_ID)
    public void configChange(String config) {
        logger.info("配置内容：{}", config);
    }*/
}
