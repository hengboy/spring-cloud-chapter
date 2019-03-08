package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.consumer.ribbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * nacos discovery consumer
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-08 10:09
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudAlibabaNacosDiscoveryConsumerRibbonApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SpringCloudAlibabaNacosDiscoveryConsumerRibbonApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaNacosDiscoveryConsumerRibbonApplication.class, args);
        logger.info("「「「「「Nacos discovery consumer start successfully.」」」」」");
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
