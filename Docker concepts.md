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
