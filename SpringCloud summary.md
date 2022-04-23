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
2. In properties file next settings should be applied
    **spring.application.name=spring-cloud-config-server**

    **server.port=8888**

- If we want to use local git repo

    **spring.cloud.config.server.git.uri=file:///E:/git-localconfig-repo**
- Using cloud git repo

    **spring.cloud.config.server.git.uri=https://github.com/Dronworks/config-server-data.git**
- To avoid github master which is main error

    **spring.cloud.config.server.git.default-label=master**

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