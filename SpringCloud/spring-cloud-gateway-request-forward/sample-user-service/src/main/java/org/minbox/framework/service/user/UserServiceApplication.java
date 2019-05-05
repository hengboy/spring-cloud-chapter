package org.minbox.framework.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-05 11:40
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class UserServiceApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        logger.info("「「「「「用户服务启动完成.」」」」」");
    }

    @GetMapping(value = "/index")
    public String index() {
        return "this is user index";
    }
}
