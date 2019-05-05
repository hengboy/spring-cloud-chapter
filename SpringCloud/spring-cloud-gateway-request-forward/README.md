在上一篇文章[Spring Cloud GateWay 路由转发规则介绍](http://blog.yuqiyu.com/spring-cloud-gateway-predicate.html)中我们讲解了`SpringCloud Gateway`内部提供的`断言、谓语`，让我们可以组合更精确的业务场景进行请求，既然`SpringCloud GateWay`担任了`网关`的角色，在之前`Zuul`可以通过服务名进行自动转发，`SpringCloud Gateway`是否可以实现自动转发呢？

### 初始化Gateway服务

`Spring Cloud Gateway`可以根据配置的`断言、谓语`进行满足条件转发，也可以自动同步`服务注册中心`的服务列表进行指定`serviceId`前缀进行转发，这里的`serviceId`是业务服务的`spring.application.name`配置参数。

#### SpringCloud 版本控制依赖

把`SpringCloud`的版本依赖添加到`pom.xml`内，如下所示：

```xml
//...
<properties>
  <java.version>1.8</java.version>
  <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
</properties>
<!--Spring Cloud 版本控制-->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring-cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
//...
```



我们本章使用`Eureka`作为服务注册中心来完成服务请求转发讲解，需要把`Spring Cloud Gateway`网关项目作为一个`Client`注册到`Eureka Server`，先来看下添加的依赖，`pom.xml`如下所示：

```xml
//...
<dependencies>
  <!--Spring Cloud Gateway-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
  </dependency>
  <!--Eureka Client-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
</dependencies>
//....
```

接下来我们需要开启`Gateway`服务注册中心的发现配置，开启后才能自动同步`服务注册中心的服务列表`，`application.yml`配置文件如下所示：

```yaml
# 服务名称
spring:
  application:
    name: spring-cloud-gateway
  # 开启 Gateway 服务注册中心服务发现
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
# Eureka Server 配置
eureka:
  client:
    service-url:
      defaultZone: http://localhost:10000/eureka/
# 配置Gateway日志等级，输出转发细节信息
logging:
  level:
    org.springframework.cloud.gateway: debug
```

配置参数解释如下所示：

- `spring.application.name`：服务名
- `spring.cloud.gateway.discovery.locator.enabled`：开启`SpringCloud Gateway`的注册中心发现配置，开启后可自动从服务注册中心拉取服务列表，通过各个服务的`spring.application.name`作为前缀进行转发，该配置默认为`false`。
- `eureka.client.service-url.defaultZone`：配置`Eureka Server`默认的空间地址
- `logging.level.org.springframework.cloud.gateway`：设置`SpringCloud Gateway`日志等级为`debug`，用于输出转发的细节日志，方便查看细节流程。

#### 注册网关到Eureka

在入口类添加对应的注解，开启服务自动注册，如下所示：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
}
```



### 服务注册中心

对应上面`网关`配置的`Eureka Server`的地址，我们需要添加对应的配置，`pom.xml`如下所示：

```xml
//...
<dependencies>
  <!--Eureka Server-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
  </dependency>
</dependencies>
//...
```

添加依赖后对`Eureka Server`进行配置，配置文件`application.yml`如下所示：

```yaml
# 服务名
spring:
  application:
    name: sample-eureka-server
# 端口号    
server:
  port: 10000

# Eureka 配置信息
eureka:
  client:
    service-url:
      defaultZone: http://localhost:${server.port}/eureka/
    fetch-registry: false
    register-with-eureka: false
```

这里我们修改默认的端口号为`10000`，为了匹配在`网关项目`的配置信息，至于`fetch-registry`、`register-with-eureka`可以去我之前的文章查看，[SpringCloud组件：将服务提供者注册到Eureka集群](<http://blog.yuqiyu.com/spring-cloud-eureka-high-provider.html>)

#### 开启Eureka Server

我们通过`@EnableEurekaServer`注解来开启服务，如下所示：

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

> `网关`、`服务注册中心`我们都已经准备好了，下面我们可以编写业务逻辑服务，来验证`SpringCloud Gateway`具体是否可以根据`serviceId`进行转发请求。

### 单服务

我们简单编写一个`GET`请求地址，输出字符串信息，`pom.xml`添加依赖如下所示：

```xml
<dependencies>
  <!--Web-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <!--Eureka Client-->
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
</dependencies>
```

配置文件`application.yml`如下所示：

```yaml
# 服务名
spring:
  application:
    name: user-service
# 注册到Eureka
eureka:
  client:
    service-url:
      defaultZone: http://localhost:10000/eureka/
# 服务端口号
server:
  port: 9090

```

配置该服务的服务名称为`user-service`，这里对应`SpringCloud Gateway`的`serviceId`。

#### 注册服务到Eureka

```java
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
```

`user-service`提供了`/index`的请求地址，当访问时，会对应输出`this is user index`。

#### 测试服务请求转发

接下来我们进行验证，测试顺序如下所示：

**第一步：启动Eureka Server**

**第二步：启动SpringCloud Gateway**

启动成功后控制台会打印响应的注册到`Eureka`的日志信息，如下所示：

```sh
DiscoveryClient_SPRING-CLOUD-GATEWAY/192.168.1.56:spring-cloud-gateway: registering service...
Netty started on port(s): 8080
```

`SpringCloud Gateway`内部通过`Netty`完成`WebServer`的请求转发。

**第三步：启动user-service服务**

启动成功后控制台打印相应注册日志，如下所示：

```sh
DiscoveryClient_USER-SERVICE/192.168.1.56:user-service:9090: registering service...
Tomcat started on port(s): 9090 (http) with context path ''
```

**第四步：测试访问**

`SpringCloud Gateway`会每间隔`30秒`进行重新拉取服务列表后路由重定义操作，日志信息如下所示：

```sh
# Spring Cloud Gateway
RouteDefinition CompositeDiscoveryClient_SPRING-CLOUD-GATEWAY applying {pattern=/SPRING-CLOUD-GATEWAY/**} to Path
RouteDefinition CompositeDiscoveryClient_SPRING-CLOUD-GATEWAY applying filter {regexp=/SPRING-CLOUD-GATEWAY/(?<remaining>.*), replacement=/${remaining}} to RewritePath
RouteDefinition matched: CompositeDiscoveryClient_SPRING-CLOUD-GATEWAY
# User Service
RouteDefinition CompositeDiscoveryClient_USER-SERVICE applying {pattern=/USER-SERVICE/**} to Path
RouteDefinition CompositeDiscoveryClient_USER-SERVICE applying filter {regexp=/USER-SERVICE/(?<remaining>.*), replacement=/${remaining}} to RewritePath
RouteDefinition matched: CompositeDiscoveryClient_USER-SERVICE
```

通过上面的日志信息我们已经可以推断出`SpringCloud Gateway`映射`spring.application.name`的值作为服务路径前缀，不过是大写的，预计我们可以通过`http://localhost:8080/USER-SERVICE/index`访问到对应的信息。

访问测试如下：

```sh
~ curl http://localhost:8080/USER-SERVICE/index
this is user index
```

通过网关访问具体服务的格式：`http://网关IP:网关端口号/serviceId/**`

### 多服务的负载均衡

**如果`Eureka Server`上有两个相同`serviceId`的服务时，`SpringCloud Gateway`会自动完成负载均衡。**

复制一个`user-service`服务实例，修改`服务端口号`，如下所示：

```yaml
# 服务名称
spring:
  application:
    name: user-service
# Eureka Server
eureka:
  client:
    service-url:
      defaultZone: http://localhost:10000/eureka/
# 服务端口号
server:
  port: 9091
```

在复制的项目内使用相同的`spring.application.name`保持`serviceId`一致，只做端口号的修改，为了区分`GateWay`完成了负载均衡，我们修改`/index`请求的返回内容如下所示：

```java
@GetMapping(value = "/index")
public String index() {
  return "this is user lb index";
}
```

访问`http://localhost:8080/USER-SERVICE/index`，输出内容如下所示：

```sh
this is user lb index
this is user index
this is user lb index
this is user index
...
```

### 总结

通过本章的讲解，我们已经对`SpringCloud Gateway`的转发有一个简单的理解，通过从服务注册中心拉取服务列表后，自动根据`serviceId`映射路径前缀，同名服务多实例时会自动实现负载均衡。

### 源码位置

`Gitee`：<https://gitee.com/hengboy/spring-cloud-chapter/tree/master/SpringCloud/spring-cloud-gateway-request-forward> 

`ApiBoot`：<https://gitee.com/hengboy/api-boot>、<https://github.com/hengboy/api-boot> 

