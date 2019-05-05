`Spring`在因`Netflix`开源流产事件后，在不断的更换`Netflix`相关的组件，比如：`Eureka`、`Zuul`、`Feign`、`Ribbon`等，`Zuul`的替代产品就是`SpringCloud Gateway`，这是`Spring`团队研发的网关组件，可以实现限流、安全认证、支持长连接等新特性。

## Spring Cloud Gateway

`Spring Cloud Gateway`是`SpringCloud`的全新子项目，该项目基于`Spring5.x`、`SpringBoot2.x`技术版本进行编写，意在提供简单方便、可扩展的统一API路由管理方式。
**概念解释：**

- `Route（路由）`：路由是网关的基本单元，由ID、URI、一组Predicate、一组Filter组成，根据Predicate进行匹配转发。
- `Predicate（谓语、断言）`：路由转发的判断条件，目前`SpringCloud Gateway`支持多种方式，常见如：`Path`、`Query`、`Method`、`Header`等。
- `Filter（过滤器）`：过滤器是路由转发请求时所经过的过滤逻辑，可用于修改请求、响应内容。



**Spring Cloud GateWay 工作流程如下所示**：
![spring-cloud-gateway-work.png](https://upload-images.jianshu.io/upload_images/4461954-e8f9d2b9eae8fcab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


客户端向`Spring Cloud Gateway`发出请求。如果网关处理程序映射确定请求与路由匹配，则将其发送到网关Web处理程序。此处理程序运行时通过特定于请求的筛选链发送请求。过滤器被虚线分隔的原因是过滤器可以在发送代理请求之前或之后执行逻辑。执行所有“预”过滤逻辑，然后发出代理请求。在发出代理请求后，将执行“post”过滤器逻辑。

## 开始使用

`Spring Cloud Gateway`目前有两种方式进行配置：

- `application.yml`配置文件方式
- 通过@Bean注解`RouteLocator`方法返回值

在本章会侧重针对配置文件方式进行讲解，当然`RouteLocator`方式也会简单的告诉大家的使用方式。

### 添加依赖

添加`Spring Cloud Gateway`相关依赖，`pom.xml`如下所示：

```xml
//...省略部分内容
<properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>

    <dependencies>
        <!--Spring Cloud Gateway-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

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
//...省略部分内容
```

### Spring Cloud Gateway Predicates

在我们开始本章内容之前我们要来先了解下`Spring Cloud Gateway`内部提供的所有`谓语、断言`，这样我们才能目标性的进行学习，我整理出来了一个脑图，如下所示：

![SpringCloud-Gateway-Predicates.png](https://upload-images.jianshu.io/upload_images/4461954-1b814fb4ee7229a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


每一个`Predicate`的使用，你可以理解为：`当满足这种条件后才会被转发`，如果是多个，那就是都满足的情况下被转发。

### Path 方式匹配转发

通过`Path`转发示例，我们讲解下上面的两种配置，分别是`application.yml`以及`RouteLocator`。

#### 配置文件匹配地址转发

我们在`application.yml`配置文件内添加对应的路由配置，如下所示：

```yaml
spring:
  application:
    name: spring-cloud-gateway-sample
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            # 匹配路径转发
            - Path=/api-boot-datasource-switch.html
# 端口号
server:
  port: 9090

```

**先来解释下`route`的组成部分：**

- `id`：路由的ID
- `uri`：匹配路由的转发地址
- `predicates`：配置该路由的断言，通过`PredicateDefinition`类进行接收配置。



在上面的配置中，当访问`http://localhost:9090/api-boot-datasource-switch.html`时就会被自动转发到`http://blog.yuqiyu.com/api-boot-datasource-switch.html`，这里要注意完全匹配`Path`的值时才会进行路由转发。

访问效果如下所示：

![spring-cloud-gateway-path-predicate.png](https://upload-images.jianshu.io/upload_images/4461954-f943fbd197232b2e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




#### RouteLocator 匹配路径转发

在上面的配置中，如果使用`RouteLocator`方式该怎么进行配置呢？

如下所示：

```java
@Bean
public RouteLocator routeLocator(RouteLocatorBuilder builder) {
  return builder.routes()
    .route("blog", r -> 
           r.path("/api-boot-datasource-switch.html").uri("http://blog.yuqiyu.com"))
    .build();
}
```

### Before 方式匹配转发

当部署有访问时间限制的接口时，我们可以通过`Before Predicate`来完成某一个时间点之前允许访问，过时后则不允许转发请求到具体的服务，配置如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Before=2019-05-01T00:00:00+08:00[Asia/Shanghai]
```

在上面配置中，我们允许`2019-05-01`日凌晨之前通过路由转发到`http://blog.yuqiyu.com`，通过查看`org.springframework.cloud.gateway.handler.predicate.BeforeRoutePredicateFactory`源码我们发现，`Spring Cloud Gateway`的`Before`断言采用的`ZonedDateTime`进行匹配时间，这里要注意存在时区的问题，需要配置`[Asia/Shanghai]`作为中国时区。

### After 方式匹配转发

`After Predicate`与`Before`配置使用一致，匹配某一个时间点之后允许路由转发，如下所示配置：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - After=2019-04-29T00:00:00+08:00[Asia/Shanghai]
```

在上面配置中允许`2019-04-29`凌晨之后进行转发到`http://blog.yuqiyu.com`。

### Between 方式匹配转发

那如果是一个时间段内允许请求转发，通过`Before`、`After`组合配置也可以完成，不过`Spring Cloud Gateway`还是提供了`Between`方式，如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Between=2019-04-29T00:00:00+08:00[Asia/Shanghai], 2019-05-01T00:00:00+08:00[Asia/Shanghai]
```

在上面配置中，允许在`2019-04-29`日凌晨后 & `2019-05-01`凌晨之前请求转发到`http://blog.yuqiyu.com`。

### Cookie 方式匹配转发

`Spring Cloud Gateway` 还提供了根据`Cookie`值的方式匹配转发请求，如果请求中所携带的`Cookie`值与配置的`Predicate`匹配，那么就可以被允许转发到指定地址，如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Cookie=hengboy, yuqiyu
```

在上面配置中，如果客户端发送请求时携带了`"hengboy=yuqiyu"`的Cookie信息，则允许请求转发。

**测试Cookie方式转发：**

```sh
curl http://localhost:9090 --cookie "hengboy=yuqiyu"
```

通过上面方式我们是可以成功转发请求的，如果我们修改`Cookie`的值，就会导致`无法转发`，出现404。

### Header 方式匹配转发

`Spring Cloud Gateway`可以根据发送请求的`Header`信息进行匹配转发，加入我们可以根据`X-Request-Id`的值进行匹配，如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Header=X-Request-Id, \d+
```

在上面配置中，如果`X-Request-Id`的值为数字，那么就可以转发到`http://blog.yuqiyu.com`，我们通过如下方式进行测试：

```sh
curl http://localhost:9090 -H "X-Request-Id:123456"
```

如果头信息为`X-Request-Id:abc`时，就会转发失败，出现404。

### Host 方式匹配转发

`Spring Cloud Gateway`可以根据`Host`主机名进行匹配转发，如果我们的接口只允许`**.yuqiyu.com`域名进行访问，那么配置如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Host=**.yuqiyu.com
```

测试如下所示：

```sh
 1. curl http://localhost:9090 -H "Host: yuqiyu.com"      	// 匹配
 2. curl http://localhost:9090 -H "Host: api.yuqiyu.com"		// 匹配
 3. curl http://localhost:9090 -H "Host: admin.yuqiyu.com"  // 匹配
 3. curl http://localhost:9090 -H "Host: hengboy.com"  	    // 不匹配
```



### 请求方式 方式匹配转发

`Rest`请求风格的接口内往往会存在多种请求方式的接口，如果我们的接口只允许`POST`请求访问，那么配置如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Method=POST
```

发送`GET`请求测试：

```sh
~ curl http://localhost:9090
{"timestamp":"2019-04-29T06:27:41.121+0000","path":"/","status":404,"error":"Not Found","message":null}
```

我们的请求并未被`Spring Cloud Gateway`进行转发，那么我们再来通过`POST`请求进行测试：

```sh
curl -X POST http://localhost:9090
```

是可以被转发到目标地址`uri`的，不过我的这个博客是`OSS`部署的，阿里云限制了`POST`访问，尽管如此我们也证明了可以转发。

### 请求参数 方式匹配转发

`Spring Cloud GateWay`还支持根据指定的参数进行匹配，`Query`方式的`Predicate`也有两种方式匹配情况，如下所示：

- 请求中存在`xxx`参数

  ```yaml
  spring:
    cloud:
      gateway:
        routes:
          - id: blog
            uri: http://blog.yuqiyu.com
            predicates:
              - Query=xxx
  ```

  我们通过`curl http://localhost:9090\?xxx\=123`是可以被成功转发的，只要参数存在`xxx`就会被成功转发，否则出现404转发失败。

- 请求中存在`xxx`参数且值为`zzz`

  ```yaml
  spring:
    cloud:
      gateway:
        routes:
          - id: blog
            uri: http://blog.yuqiyu.com
            predicates:
              - Query=xxx, zzz
  ```

  根据上面配置，我们限定了参数`xxx`必须为`zzz`时才会被成功转发，否则同样会出现404抓发失败。

### 请求路径 方式匹配转发

`Spring Cloud Gateway`提供了请求路径变量方式匹配转发，如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Path=/article/{articleId}
```

在上面配置中`{articleId}`是一个路径变量，可以是任意值，匹配`/article/1`、`/article/abc`等，测试如下所示：

```sh
~ curl http://localhost:9090/article/1			// 匹配
~ curl http://localhost:9090/article/abc		// 匹配
~ curl http://localhost:9090/article/1/1		// 不匹配
```

### 请求IP 方式匹配转发

`Spring Cloud Gateway`可以限制允许访问接口的客户端`IP`地址，配置后只对指定`IP`地址的客户端进行请求转发，配置如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - RemoteAddr=192.168.1.56/24
```

在上面我们配置了`192.168.1.56/24`，其中`192.168.1.56`是客户端的`IP`地址，而`24`则是子网掩码。

### 组合示例

相同的`Predicate`也可以配置多个，请求的转发是必须满足所有的`Predicate`后才可以进行路由转发，组合使用示例如下所示：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: blog
          uri: http://blog.yuqiyu.com
          predicates:
            - Query=author, hengboy
            - Query=yuqiyu
            - Method=GET
            - Cookie=hengboy, yuqiyu
            - Header=X-Request-Id, \d+
            - RemoteAddr=192.168.1.56/24
```

## 总结

本章节讲解了`Spring Cloud Gateway`的相关`谓词、断言`基本使用方式，`GateWay`内部提供了很多种灵活的路由转发规则，在同一个路由内存在多个`Predicate`时，同时满足规则后请求才会被路由转发。



## 源码位置

`Gitee`：<https://gitee.com/hengboy/spring-cloud-chapter/tree/master/SpringCloud/spring-cloud-gateway>

`ApiBoot`：<https://gitee.com/hengboy/api-boot>、<https://github.com/hengboy/api-boot>
