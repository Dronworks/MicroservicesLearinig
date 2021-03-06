# Config Server
### To work with config server we need to **enable a client** and **create a config server**.

## ***CLIENT***: 
1. In **start.spring.io** select
    ```
    Config Client SPRING CLOUD CONFIG
    Client that connects to a Spring Cloud Config Server to fetch the application's configuration.
    ```
    In pom this will look like this:
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    ```
2. Also we need to access the config server from properties:
- Get configurations from config server - optional: is for starting service while config server not active.

    **spring.config.import=optional:configserver:http://localhost:8888**
- Config what setting to use - this is application name and when we are using config server we may want to have same name.

    **spring.application.name=limits-service**
- If we want to separate application name and config file name we can use

    **spring.cloud.config.name=**
- To get settings from different profile - This profile is of spring app. And if the name is the same as in config server, it will load the profile from there

    **spring.profiles.active=qa**
- If the name is not the same or we want different profile for server and for config - need to add next line.

    **spring.cloud.config.profile=dev**
- If we want to access properties from another branch we can add

    **spring.cloud.config.label=mybranch**


## ***SERVER***
1. In **start.spring.io** select
    ```
    Config Server SPRING CLOUD CONFIG
    Central management for configuration via Git, SVN, or HashiCorp Vault.
    ```
    In pom this will look like this:
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    ```
2. In application.properties file next settings should be applied
- Regular properties

    **spring.application.name=spring-cloud-config-server**

    **server.port=8888**

- If we want to use local git repo

    **spring.cloud.config.server.git.uri=file:///E:/git-localconfig-repo**
- Using cloud git repo

    **spring.cloud.config.server.git.uri=https://github.com/Dronworks/config-server-data.git**
- To avoid github master which is main error

    **spring.cloud.config.server.git.default-label=master**

3. In the main application add config server
    ```
    @SpringBootApplication
    @EnableConfigServer
    public class SpringCloudConfigServerApplication {
    ```

3. In the git repository we need to create a file with the app name and put our properties there for example

    **limits-service.properties**
    ```
    limits-service.minimum=09
    limits-service.maximum=132
    ```

## ***Accessing the Config***
### From browser we can type something like this
http://localhost:8888/limits-service/default

{server ip/fqdn}:{port}/{service name}/{profile name}/{branch name}

**NOTE** when accessing the link we can see the results are ordered by PRIORITY in the *propertySources*

### From code we can access using ConfigurationProperties
```
@Data // We need setters (and maybe default constructor) in order to fill those fields
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("limits-service") // The root of the configurations
public class LimitConfiguration {

	private int minimum;
	private int maximum;
	
}
```

**NOTE** If we want to access the config by profiles we need to add more files to the git repository like this:

For profile **QA**

Add a file **limits-service-qa.properties**

And by changing the profile to QA in the client we will get those properites. Also the link will be http://localhost:8888/limits-service/qa

**NOTE2** If we have two properties one of them we rewrite in the profile file and one not, the config server will fill one from the default file and one from the profile (overwrite) hence the priority order.

**NOTE3** if we use setting with "-" it will automatically convert it: **test-app** to **testApp**. If the conversion is more complex we can use **@JsonProperty("theName")**

## ***Changing the Config***
When we make change to the git repository we need to refresh the Spring apps that use this config. To do so we have to install actuator for the application:

```
Spring Boot Actuator OPS
Supports built in (or custom) endpoints that let you monitor and manage your application - such as application health, metrics, sessions, etc.
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```   

And use the link:
    
**{app link}/actuator/refresh**

**NOTE** to change All instances of a service we need to add a support for spring bus (usually by rabbitmq). To do so see:

**https://www.baeldung.com/spring-cloud-bus**

# ===============================================================

# EUREKA -naming service
**enable a client** and **create an Eureka server**.

## ***CLIENT***: 
1. In **start.spring.io** select
    ```
    Eureka Discovery Client SPRING CLOUD DISCOVERY
    A REST based service for locating services for the purpose of load balancing and failover of middle-tier servers.
    ```
    In pom this will look like this:
    ```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. In application.properties we need to set the link to register.

    **eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka**

3. To use the naming server in order to communicate between apps (before we use api gateway) we need to add an annotation to the proxy class:
    ```
    @FeignClient(value = "currency-exchange")
    public interface CurrencyExchangeProxy {
    ```
    And thats it! See more info in Additional Info - Feign

3. **NOTE** in older versions of spring we had to add next annotation to man application
    ```
    @EnableDiscoveryClient
    ```
4. **NOTE2** in dockers there are no local hosts because of different environments (different dockers) so **OR** we can change to the name of the server in **properties file** to naming-server (as we gave to the image) - **OR** in **docker-compose.yaml**

## ***SERVER***
1. In **start.spring.io** select
    ```
    Eureka Server SPRING CLOUD DISCOVERY
    spring-cloud-netflix Eureka Server.
    ```
    Note we can also select the config server client, devtools, actuator... But they are not neccessary.

    In pom this will look like this:
    ```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    ```
2. In application.properties file add
- Regular properties

    **server.port=8761**

    **spring.application.name=naming-server**

- We dont want to register self

    **eureka.client.register-with-eureka=false**

    **eureka.client.fetch-registry=false**
    
3. In the main application add eureka server
    ```
    @SpringBootApplication
    @EnableEurekaServer
    public class NamingServerApplication {
    ```


# ===============================================================

# API-GATEWAY
Api Gateway letting us have a single entry point for all our and external apps it lets us centarlizing:
- Set a single Authentication, Authorization and Security configurations.
- Limit rates when needed.
- Work with faults (when server is unavailable).
- Aggrigate service
- Logging
- Debugging if needed
- Filtering

**enable a client** and **create an Api-Gateway server**.

## ***CLIENT***: 
1. Client side is easy, we just need in the feign proxy to point to API-GATEWAY like this
    ```
    @FeignClient(value = "API-GATEWAY")
    public interface CurrencyExchangeProxy {

        @GetMapping("/CURRENCY-EXCHANGE/currency-exchange/from/{from}/to/{to}")
        public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
    }
    ```

## ***SERVER***
1. In **start.spring.io** select both
    ```
    Eureka Discovery Client SPRING CLOUD DISCOVERY
    A REST based service for locating services for the purpose of load balancing and failover of middle-tier servers.

    Gateway SPRING CLOUD ROUTING
    Provides a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as security, monitoring/metrics, and resiliency.
    ```
    Note we can also select the config server client, devtools, actuator... But they are not neccessary.

    In pom this will look like this:
    ```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. In application.properties file add
- Regular properties

    **server.port=8765**

    **spring.application.name=api-gateway**

- Connect to eureka to get app urls

    **eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka**

    **spring.cloud.gateway.discovery.locator.enabled=true**

- If we want to use conventional url (lower case)
    
    **spring.cloud.gateway.discovery.locator.lower-case-service-id=true**
    

## ***Accessing the Gateway***
To work with api gateway the service api access eureka and builds an url with it, like this:

For example exchange server is registered like this **CURRENCY-EXCHANGE**

The url will be
**http://localhost:8765/CURRENCY-EXCHANGE** *plus the endpoint like* **/currency-exchange/from/USD/to/INR**

## ***Wokring with API-GATEWAY***

### **Filtering**
Example of filtering with same output + logging
```
@Component
public class LoggingFilter implements GlobalFilter {

private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

@Override
public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("Path of the request -> {}", exchange.getRequest().getPath());
    return chain.filter(exchange); // Return the same.
}
}
```

### **Routing**
Simple route example with headers:
```
Function<PredicateSpec, Buildable<Route>> routeFunc = p -> p.path("/get")
    .filters(f -> f
        .addRequestHeader("MyHeader", "MyUri")
        .addRequestParameter("Param", "MyValue"))
    .url("http://httpbin.org:80")
return builder.routes().route(routeFunc).build();
``` 
And we can access next link and see our sent param and headers
    
**localhost:8765/get**

We can route the calls ourselves (Also we can disconnect from discovery, **by closing the setting in application.properties**, if we would like...)

``` 
@Configuration
public class ApiGatewayConfiguration {

@Bean
public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder.routes()
            .route(p -> p.path("/get") // can be matched on multiple parameters!
            .filters(f -> f.addRequestHeader("MyHeader", "MyUri")
                    .addRequestParameter("SomeParam", "Param"))
            .uri("http://httpbin.org:80"))
            .route(p -> p.path("/currency-exchange/**")
                    .uri("lb://CURRENCY-EXCHANGE")) // Load Balance - gets from EUREKA
            .route(p -> p.path("/currency-conversion/**")
                    .uri("lb://CURRENCY-CONVERSION")) // Load Balance - gets from EUREKA
            .route(p -> p.path("/currency-conversion-feign/**")
                    .uri("lb://CURRENCY-CONVERSION")) // Load Balance - gets from EUREKA
            .route(p -> p.path("/currency-conversion-new/**")
                    .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                            "/currency-conversion-feign/${segment}")) // Rewrite path with the segment that comes after
                    .uri("lb://CURRENCY-CONVERSION"))
            .build();
    }

}
```

# ===============================================================

# ZIPKIN
- Zipkin lets us trace the requests. One of the important things to trace requests is to assign an id to them, so we can see the request path between services. 

- When we use log in the application we will see the ID added automatically to each logged message.

    *2022-04-26 18:15:02.978  INFO **[currency-exchange,05bfdd8d71fabd95,05bfdd8d71fabd95]** 5732 --- [nio-8000-exec-5] CurrencyExchangeController               : retrieveExchangeValue*

    we will see this id in the tracing path in zipkin.

- Rabbit MQ is good to store messages when zipkin is offline.

**enable a client** and **create an Api-Gateway server**.

## ***CLIENT***: 
1. In **start.spring.io** select
    ```
    Sleuth OBSERVABILITY
    Distributed tracing via logs with Spring Cloud Sleuth.
    
    Zipkin Client OBSERVABILITY
    Distributed tracing with an existing Zipkin installation and Spring Cloud Sleuth Zipkin.
    ```
    In pom this will look like this:
    ```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-sleuth-zipkin</artifactId>
    </dependency>
    ```

    **IF** we want to use RABBITMQ we need to add next dependency:
    ```
    Spring for RabbitMQ MESSAGING
    Gives your applications a common platform to send and receive messages, and your messages a safe place to live until received.
    ```
    In pom this will look like:
    ```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    ```

2. Also in application.properties we need to set the logging amout as well as adding the ids, where tracing percentage of requests 1.0 - all | 0.1 - 10% | 0.05 - 5%

    **spring.sleuth.sampler.probability=1.0**

## ***SERVER***
There are three ways to start zipkin.
1. Easiest - via docker:

    **docker run -p 9411:9411 openzipkin/zipkin:2.23**
2. Download and run jar:

    **java -jar zipking-server-2.5.2-exec.jar**

3. Install Rabbit MQ on your PC or Run it via docker. Then run the zipkin using docker or jar. 

    **NOTE** see docker-compose.yaml
- Docker - in docker-compose.yaml in zipkin server add 
    ```
    RABBIT_URI: amqp://guest:guest@rabbitmq:5672
    ```
- Jar:
    ```
    set RABBIT_URL=amqp://guest:guest@rabbitmq:5672
    java -jar zipking-server-2.5.2-exec.jar
    ```

# ===============================================================


# Additional Info - Services consuming using FEIGN
- To install add feign dependency
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    ```
- In application Main add Feign support
    ```
    @EnableFeignClients
    public class CurrencyConversionServiceApplication {
    ```
- Create feign proxy client
    ```
    @FeignClient(value = "currency-exchange", url = "localhost:8000")
    public interface CurrencyExchangeProxy {
        @GetMapping("/currency-exchange/from/{from}/to/{to}")
        public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
    ```
- Feign proxy client using Eureka
    ```
    // Eureka:
    @FeignClient(value = "currency-exchange")
    public interface CurrencyExchangeProxy {
        @GetMapping("/currency-exchange/from/{from}/to/{to}")
        public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
    ```
- **NOTE - IN OLDER VERSIONS** we used Ribbon. To work with it we had to add a dependency (> 2.0.0 it is ...starter-netflix-ribbon)
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
    ```
    In the properties file we need to set a list of LB servers
    ```
    currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001
    ```
    In the proxy class we need to make this settings
    ```
    @FeignClient(name="currency-exchange-service")
    @RibbonClient(name="currency-exchange-service)
    public interface CurrencyExchangeProxy {
    ```

# ============================================

# Additional Info - Same fields in the consumer and producer services
If we access one service from another and one service have all the data that the second is returning plus additional data we can do something cool with the classes:

- Producer service Class
    ```
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiplier;
    private String environment;
    ```
- Consumer service Class
    ```
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiplier;
    private BigDecimal quantity;
    private BigDecimal totalCalculatedAmount;
    private String environment;
    ```
- In the Consumer controller we can call the producer and get from it the CUNSUMER class!
    ```
    ResponseEntity<CurrencyConversion> forEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriConfigs);
    CurrencyConversion currencyConversion = forEntity.getBody();
    ```
    and add the consumer data
    ```
    currencyConversion.setQuantity(quantity);
    currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiplier()));
    ```

# ============================================

# Additional Info - Actuator
Actuator add lots of endpoints for our application for managing. 

For example: **actuator/health** - will return the status of our app. Health also has Readiness and Livingless (see kubernetes info.md).

To enable actuator:
in **start.spring.io** select
```
Spring Boot Actuator OPS
Supports built in (or custom) endpoints that let you monitor and manage your application - such as application health, metrics, sessions, etc.
```
In pom it will look like this:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
In application.properites we need to open the links we need using

**management.endpoints.web.exposure.include={apps names / '*' for all}**


# ============================================

# Additional Info - DevTools
Dev tools help us to fast update application after code changes (if app takes 10 seconds to start, this will make the cahnges to appear in 2 seconds).

To enable dev tools:
in **start.spring.io** select
```
Spring Boot DevTools DEVELOPER TOOLS
Provides fast application restarts, LiveReload, and configurations for enhanced development experience.
```
In pom it will look like this:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```
To use Devtools - after changing the project run build. OR in eclipse save the file.

# ============================================

# Additional Info - Fault Tollerance
In microservices one service is depending on another service. If one service is down all the chain of services will fail. Also slow service may impact our chain! How do we deal with it?

Strategies:
- **Default fallback** response.
    
    For example: a shopping site is not available we can sent back some default shopping products untill the site is available.

- **Circuite breaker** - if we know next service on the chain is down, can we stop the chain before the request goes there?

- **Temporary failure** - can we retry few times before returning some default or bad response.

- **Limit access** - we can limit access to a service that is low on resources for example.

Spring solution: **Resilience4j** link: https://resilience4j.readme.io/docs/getting-started-3

**NOTE** Resilience4j was developed as a successer of Hysterix after java 8 added the functional programming.

## ***CLIENT***: 
1. In **start.spring.io** select
    ```
    Resilience4J SPRING CLOUD CIRCUIT BREAKER
    Spring Cloud Circuit breaker with Resilience4j as the underlying implementation.
    ```
    In pom this will look like this:
    ```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>
    ```

**NOTE** By resilience site - you need actuator, aop, and resilience-springboot2 for it to work if you dont want to get it from start.spring.io


## ***Client - Code examples***
All the examples go throgh a controller that we create for this behavior.

**@RestController
public class CircuitBreakerController {**

1. **Retries** - lets put some dummy server, that doesn't exist, and we can see the retry.
    - Default retry (name="default") - the default retry we can see by logging the firest line of the function. We can see by default it retries 3 times, and only then return error.
        ```
        @RestController
        public class CircuitBreakerController {

            Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


            @GetMapping("/sample-api")
            @Retry(name="default")
            public String sampleApi() {
                logger.info("Sample api call received");
                ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy", String.class);

            return forEntity.getBody();
        }
        ```
    - Our Custom retries - for this we will change the default name to some other name:
        ```
        @Retry(name="some-name")
        ```
        And in application.properties we need to add next property with our custom name.
        
        **resilience4j.retry.instances.some-name.max-attempts=5**
        
    - Retry with fallback method - we can call for a method that will create some default response on failure for us.
        ```
        @Retry(name="sample-api-prop", fallbackMethod = "hardcodedResponse")
        ...
        public String hardcodedResponse(Exception ex) {
            return "fallback-response";
        }
        ```
        **NOTE** we can same method with multiple excheption handling for example:
        ```
        public String hardcodedResponse(NullPointerException ex) { 
            return "null-pointer-response";
        }
        ```
    **More configurations for retry in application.properties**
    - Waiting time before retry 

        **resilience4j.retry.instances.sample-api-prop.wait-duration=1s**
    
    - Watiting exponential time before retry - first time we wait duration, next time we wait twice more, and next time twice more... (**Useful when service is temporary down**)

        **resilience4j.retry.instances.sample-api-prop.enable-exponential-backoff=true**

But what if the server is actually down? Retries wont work. And we can use circuite breaker
    
2. **Circuite Breaker** we can test circuite breaker with watch command. **watch -n 0.1 curl http://localhost:8000/sample-api** this will send 10 requests per second. With the circuite breaker we can see the fallback in the app, and in the log we can see that at start we are entering the endpoint funcition, and after a while we just getting the fallback resopnse without entering the function!

    Code example:

    ```
    @GetMapping("/sample-api")
    @CircuitBreaker(name="default", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy", String.class);
        return forEntity.getBody();
    }
    ```

    Circuite Breaker has 3 states: 
    - Closed - all requests going to the endpoint and return good.
    - Opened - all requests are blocked and default fallback is returned.
    - Half-opened - some of the request actually getting through and the rest are automatically default fallback.

    ***Lifecicle:*** Once we start, the circuite breaker starts in a **Closed state**. After like 90% of the requests are failing, it we become **Opened state** and all the responses are fallback. Now it will wait some time and become **Half-opened state** it will try to send an amount of messages to the actual endpoint(conigurable) and if the next requests will all success, it we become **Closed** if not it will return for more time to **Open**.
    
    We can see existing configuration for the circuite breaker in the https://resilience4j.readme.io/docs/circuitbreaker.
    
    Example: **resilience4j.circuitbreaker.instances.default.failure-rate-threshold=90** This will open the breaker after 90% of the requests fail. (default is 50%)

3. **Rate Limiter** - for example we want to send only 10000 requests in 10 seconds. Configurring limits (we can configure different limits for different names):

    Code Example:

    ```
    @GetMapping("/sample-api")
    @RateLimiter(name="sample-api")
    public String sampleApi() {
        logger.info("Sample api call received");
        return "Sample";
    }
    ```

    Example: 2 requests in every 10 seconds:
    
    **resilience4j.ratelimiter.instances.sample-api.limit-for-period=2**

    **resilience4j.ratelimiter.instances.sample-api.limit-refresh-period=10s**

4. **Concurrent calls limiter** - we can set how many concurrent calls can be made same time to a service. This is called **bulkhead**.

    Code Exmaple:
    ```
    @GetMapping("/sample-api")
    @Bulkhead(name="default")
    public String sampleApi() {
        logger.info("Sample api call received");
        return "Sample";
    }
    ```

    In application.properties:
    
    **resilience4j.bulkhead.instances.default.max-concurrent-calls=10**


**NOTE** in older spring versions Hysticx was in use.
