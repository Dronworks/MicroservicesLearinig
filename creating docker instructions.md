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