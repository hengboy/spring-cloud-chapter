package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * nacos discovery consumer
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-08 13:20
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudAlibabaNacosDiscoveryConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaNacosDiscoveryConsumerFeignApplication.class, args);
    }

    /**
     * 实例化RestTemplate
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
