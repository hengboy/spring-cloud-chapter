package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.consumer.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign消费者控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-08 13:23
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
public class FeignConsumerController {
    /**
     * 用户服务feign接口定义
     */
    @Autowired
    private UserService userService;

    /**
     * 调用feign接口
     *
     * @return
     */
    @GetMapping(value = "/user/name")
    public String getName() {
        return userService.getName();
    }
}
