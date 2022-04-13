# Creating a docker
### In order to create a docker we need two things:
### 1 - In our pom in builder part add two parts:
```
<!-- To convert to docker image we can do some changes in the
    build plugin
    1 - name of the image
        <dockerid>/some name and version
    2 - pull poclicy
        by default it is always, be we will make it
        only if image not exists locally
    -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <image>
                        <name>
                            dronworks/${project.artifactId}:${project.version}
                        </name>
                        <pullPolicy>IF_NOT_PRESENT</pullPolicy>
                    </image>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
2 - Run maven command

mvn **spring-boot:build-image**

Also possible to add -DskipTests

## Docker Compose - a way to run all the needed microservices together.
### Already installed with the docker setup
### To use we will fill the docker-compose.yaml
**Format yaml only with spaces**

**Each container that we use is a ***service*****

**We can create inner network for our apps**
```
By default Compose sets up a single network for your app. Each container for a service joins the default network and is both reachable by other containers on that network, and discoverable by them at a hostname identical to the container name.
```