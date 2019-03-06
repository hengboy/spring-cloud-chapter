package com.yuqiyu.chapter.springcloud.alibaba.nacos.config.share;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 共享配置控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-06 16:07
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */

@RestController
@RefreshScope
@RequestMapping(value = "/config")
public class ShareConfigController {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ShareConfigController.class);
    /**
     * hengboy-sca-nacos-config-share.yaml配置内容
     */
    @Value(value = "${share.basic:}")
    private String basic;
    /**
     * hengboy-common.yaml配置内容
     */
    @Value(value = "${common.version:}")
    private String commonVersion;
    /**
     * hengboy-service.yaml配置内容
     */
    @Value(value = "${service.order:}")
    private int serviceOrder;

    /**
     * 输出配置信息
     */
    @GetMapping(value = "/get")
    public void getConfig() {
        logger.info("基础配置：{}", basic);
        logger.info("公共配置：{}", commonVersion);
        logger.info("服务配置：{}", serviceOrder);
    }
}
