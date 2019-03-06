package com.yuqiyu.chapter.springcloud.alibaba.nacos.config.share;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringCloud Alibaba Nacos Config Share
 *
 * @author yuqiyu
 */
@SpringBootApplication
@RestController
@RefreshScope
@RequestMapping(value = "/config")
public class SpringCloudAlibabaNacosConfigShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaNacosConfigShareApplication.class, args);
    }

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

    @GetMapping(value = "/get")
    public String getConfig() {
        return basic + "," + commonVersion + "," + serviceOrder;
    }
}
