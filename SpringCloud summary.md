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
    

## ***Accessing the Config***
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