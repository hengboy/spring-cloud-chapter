package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.consumer.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 消费控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-08 10:09
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * ribbon方式消费请求路径
     *
     * @return
     */
    @GetMapping(value = "/user/name")
    public String ribbonConsumer() {
        return restTemplate.getForObject("http://hengboy-sca-nacos-discovery-provider/user/name", String.class);
    }
}
