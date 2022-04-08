### Download or launch local image: (short for *docker container run*)
docker **run in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image with port forwarding:
docker **run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image on different process: (-dETAUCHED)
docker **run -p 5000:5000 -d in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Run image with restart policy: (default = no, always) - will start container when docker restarts (or starts after stop) - *for example if docker restarts by itself*
docker **run -p 5000:5000 -d --restart=always in28min/todo-rest-api-h2:1.0.0.RELEASE**

### Show log of docker **on process:
docker **logs b95f35def53088cbfd5c585690dab88375c061ac59a4b7eae085997e43851f74**

### Show log contigniusly on process:
docker **logs -f b95f35def53088cbfd5c585690dab88375c061ac59a4b7eae085997e43851f74**

### Show local images:
docker **images**

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