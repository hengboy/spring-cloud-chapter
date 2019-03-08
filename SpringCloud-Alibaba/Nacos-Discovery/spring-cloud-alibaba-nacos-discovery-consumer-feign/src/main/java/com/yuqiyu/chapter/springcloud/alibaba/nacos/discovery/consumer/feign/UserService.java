package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户服务feign接口定义
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-08 13:20
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@FeignClient(name = "hengboy-sca-nacos-discovery-provider")
public interface UserService {
    /**
     * 获取用户名称
     *
     * @return
     */
    @GetMapping(value = "/user/name")
    String getName();
}
