# Concepts
* Docker only run when there is a process, hence running ubunty will end right after it starts, because no process is there.
* There is a possibility to run a command inside docker for example: **docker run ubuntu sleep 5** will sleep and stop container.
* **docker run** will run on front and will lock the terminal, add **-d** to run in background.
* hub.docker.com/explore - docker images.
* By default docker container **don't listent** to input, so app that prints *hello `input`* will not request the input and run default message. 
    
    * ading **-i** to `docker run` will ad an input to that docker 
    * adding **-t** to `docker run` will add a terminal for that input
    * hence we will run `docker run -it`

* To persist data on the docker (for example not to loose all mysql saved data) we can write my sql data to a local file system using **-v localFS:dockerFS**. Example: `docker run -v /opt/datadir:/var/lib/mysql mysql`
* Docker file should start with FROM to get the base image.
* Docker file can be made easy if after running the needed commands on docker type history and copy to notepad, and you can see the steps :)
* **!!!** To get the base os of a docker image use `docker run python:3.6 cat /etc/*release*`

### Entry point vs CMD
* A CMD is a command that run on the docker. For example to make ubuntu sleep: `docker run ubuntu sleep 10`
* BUT if we have already an ubuntu that is a sleeper wont we like to pass just the seconds? 
* Here comes the Entry point `ENTRYPOINT ["sleep"]` will make the docker on start run the sleep command and if we run `docker run ubuntu-sleeper 10` it will automatically call this function with the passed parameter.
* In **CMD** the command is replaced entirely, in **ENTRYPOINT** 
* **DEFAULT** value is done by both **ENTRYPOINT** AND **CMD**:
    ```
    ENTRYPOINT["sleep"]
    CMD["5"]
    ```
* You can **OVERRIDE** the **ENTRYPOINT** with `--entrypoint command` flag

### Multiple dockers to work together
We may run multiple dockers but how will they communicate with each other?
* We may use **--liks** - to link two containers together. For example: `docker run --link redis:redis`. Remember thats why we need to give names.The links command adds entry to hosts file (dns).
* We may have multiple links. They are deprecated!
* Better use Docker compose: 
1. Get all names and put with semicolumn in the file
2. Inside add key: **image** and values is the image to use.
3. Add **prts** and port values (as a list)
4. Add links with the names of the application in this file.
5. If image is ours and not yet build we can replace **image** with **build** with the location of application code and docker file. 

## Docker Complose
Can have different versions (like v1 - where we have app names with ':')
- v1 is bad for not local host.
- v1 is not supporting order of apps

v2 works with **services** and under are the apps.
- From v2 specify **version:** at the top of the file
- V2 creates network automatically and attach containers to that network. Lonks no need, communicate by name.
- depends_on - will add order of the containers

V3 same as v2:
- supports docker swarm
- some features removed some added
