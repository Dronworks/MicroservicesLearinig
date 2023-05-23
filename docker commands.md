### Download or launch local image: (short for *docker container run*)
docker **run in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image with port forwarding: (LEFT is our, RIGHT is docker)
docker **run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image on different process: (-dETAUCHED)
docker **run -p 5000:5000 -d in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image with restart policy: (default = no, always) - will start container when docker restarts (or starts after stop) - *for example if docker restarts by itself*
docker **run -p 5000:5000 -d --restart=always in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Show log of docker **on process:
docker **logs b95f35def53088cbfd5c585690dab88375c061ac59a4b7eae085997e43851f74**

### Show log contigniusly on process:
docker **logs -f b95f35def53088cbfd5c585690dab88375c061ac59a4b7eae085997e43851f74**

### Show running and stopped dockers
docker **ps**
docker **ps -a**

### Show local images:
docker **images**

## Remove docker
docker **rm** ***docker-id***

## Remove ALL stopped dockers
docker **rm $(docker ps --filter status=exited -q)**

### Remove image - need to remove all containers with this image before
docker **rmi** ***image-id***

## Remove ALL images not in use
docker **rmi -f $(docker images -aq)**

### Show containers:
docker **container ls**

### Show containers also stopped:
docker **container ls -a**

### Stop container from running: - *gracefully!*
docker **container stop 8121b37cec7c**

### Stop container forcefully
docker **container kill {container id}**

### Show local images:
docker **images**

### Give a tag to an image: ***Note*** *it will give a tag to an image and create new local image. "latest" is only a tag not have to be latest*
docker **tag in28min/todo-rest-api-h2:0.0.1-SNAPSHOT in28min/todo-rest-api-h2:latest**

### Get known image - only get not run (use run command)
docker **pull mysql**

### Search any image containing... better look for officials!
docker **search mysql**

### History of changes of and image
docker **history {image hashcode}**

### Info about image
docker **inspect {image hashcode}**

### Remove image from pc. Add -f if image used by container
docker **image remove {image hashcode}** 

### Pause container - will still be there (docker logs -f {container id} ) but not accessible
docker **pause {container id}**

### UnPause
docker **unpause {container id}**

### Inspect container - lots of data including ip and port 
docker **container inspect**

### Remove stopped containers from ls
docker **container prune**

### Show what happened to dockers - open in another tab
docker **events** 

### Show the top process running in a container - show processes
docker **top {container id}**

### Show cpu/memory usages 
docker **stats**

### Limit container RAM / CPU (cpu is in thousanda 5000 = 5% 100000 = 100%)
docker run -p 5002:5000 **-m 500m --cpu-quota 5000** -d in28min/todo-rest-api-h2:1.0.0.RELEASE

### Info about dockers images and more
docker **system df**

### Zipkin server (for tracing spring servers) via docker 
docker run -p **9411:9411 openzipkin/zipkin:2.23**

### Running images with docker-compose - in the folder with docker-complse.yaml
**docker-compose up**

### Execute command on a running container
docker **exec *docker-name* *command* *params***

### Stop docker gracefull and force
* docker **stop** ***docker name or image***
* docker **kill** ***docker name or image***

### Map volume
docker **run -v `localFS:dockerFS` *appname***

### Docker logs
docker **logs *dockerid***

### Get inside docker logs 
docker **attach *dockerid***

### Set Environment variable
docker **run -e VARIABLE=value app-name**

To find what environment variables are currently set to a container run `docker inspect name` and look under **Config -> Env**.

## Docker with a name 
docker **run --name=[name]**
