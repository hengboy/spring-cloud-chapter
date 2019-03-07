package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SpringCloud Alibaba Nacos Discovery 示例
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-07 13:43
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudAlibabaNacosDiscoveryProviderApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SpringCloudAlibabaNacosDiscoveryProviderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaNacosDiscoveryProviderApplication.class, args);
        logger.info("「「「「「服务提供者启动完成.」」」」」");
    }
}
