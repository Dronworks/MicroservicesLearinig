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