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

Networks: we can add **networks:** to the file with list of networks. Then in the app we may specify **networks:** that this app can use.

## Local repository
We can have our local repository just run it as docker :)

### Copy an image from Docker Hub to your registry

You can pull an image from Docker Hub and push it to your registry. The following example pulls the ubuntu:16.04 image from Docker Hub and re-tags it as my-ubuntu, then pushes it to the local registry. Finally, the ubuntu:16.04 and my-ubuntu images are deleted locally and the my-ubuntu image is pulled from the local registry.

    Pull the ubuntu:16.04 image from Docker Hub.

 docker pull ubuntu:16.04

Tag the image as localhost:5000/my-ubuntu. This creates an additional tag for the existing image. When the first part of the tag is a hostname and port, Docker interprets this as the location of a registry, when pushing.

 docker tag ubuntu:16.04 localhost:5000/my-ubuntu

Push the image to the local registry running at localhost:5000:

 docker push localhost:5000/my-ubuntu

Remove the locally-cached ubuntu:16.04 and localhost:5000/my-ubuntu images, so that you can test pulling the image from your registry. This does not remove the localhost:5000/my-ubuntu image from your registry.

 docker image remove ubuntu:16.04

 docker image remove localhost:5000/my-ubuntu

Pull the localhost:5000/my-ubuntu image from your local registry.

 docker pull localhost:5000/my-ubuntu
 
 
 ## Docker engile/cli/daemon
 Docker CLI can be installed on different machine and talk by REST with the machine where it installed:
 - Use **-H=machine:port** to run commands. e.g., `docker -H=10.123.2.1:2375 run nginx`

All docker run within namespace (for example in linux processID).
- But docker itself also has process ids inside. BUT there is not real separation so processes inside the container are same as outside. So two processes cannot have PID of 1 (default). 
- So we use namespaces. If we start a container the inside process gets the next available process on docker machine (for example 5 and 6) but in the container he gets 1 and 2/

### Limit the resources for the docker
- For example cpu by **--cpus** e.g., `docker run --cpus=.5 ubuntu`
- For examole memory bt **--memory** e.g., `docker run --memory=100m ubuntu`
- 
