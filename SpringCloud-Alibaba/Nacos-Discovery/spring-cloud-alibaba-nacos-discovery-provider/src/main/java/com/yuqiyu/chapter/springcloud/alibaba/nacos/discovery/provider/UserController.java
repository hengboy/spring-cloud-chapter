package com.yuqiyu.chapter.springcloud.alibaba.nacos.discovery.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器示例
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-07 13:44
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    /**
     * 获取用户名称
     *
     * @return
     */
    @GetMapping(value = "/name")
    public String getUserName() {
        return "恒宇少年";
    }
}
