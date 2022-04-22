# **kubectl** - *kube controller*

### Deploy image from docker
kubectl **create deployment** hello-world-rest-api **--image=** in28min/hello-world-rest-api:0.0.1.RELEASE

### Expose deployment to everyone
kubectl **expose deployment** hello-world-rest-api **--type=LoadBalancer --port=8080**

### Print latest events in the project
kubectl **get events**

### Print events in order of creation
kubectl **get events --sort-by=.metadata.creationTimestamp**

### List project pods
kubectl **get pods**

### List replicas of project
kubectl **get replicaset**

kubectl **get rs**

### List of our deployements
kubectl **get deployment**

### List of available services
kubectl **get service**

kubectl **get svc**

### See number of containers (and how many are ready i.e. 1/1), the ip of a pod
kubectl **get pods -o wide**

### Information about what is pod
kubectl **explain pods**

### Show info about a pod
kubectl **describe pod** {podid}

### Kill a pod (will by default create new one)
kubectl **delete pod** {podid}

### Deploy more scalability - 3 pods in total (two added)
kubectl **scale deployment** *hello-world-rest-api* **--replicas=3**

### Information about what is replicaset
kubectl **explain replicaset**

### Show container name, apps names and version(images associated with replicaset) in deployement
kubectl **get replicaset -o wide**

### Add more images versions to deployement
kubectl **set image deployment** *hello-world-rest-api* *hello-world-rest-api*=in28min/hello-world-rest-api:0.0.2.RELEASE 

### Delete can be for a pod/rplicaset/service
kubectl **delete** {some thing}

### Status of node componenets
kubectl **get componentstatuses**

### Get all info
kubectl **get all**

### Watch service getting ready (and maybe other things)
kubectl **get service --watch**

### Get deployments info
kubectl **get deployments**

### Get a deployment's/service yaml file
kubectl **get deployment *{deployment name}* -o yaml**

kubectl **get service *{deployment name}* -o yaml**

### Diff yamls
kubectl **diff -f *{yaml name}***

### Apply new yaml - the "-f" makes warnings - about not deploying the way it needs to be. 1 - delete 2 - deploy
kubectl **apply -f *{yaml name}*** 

### Delete deployment with label
kubectl **delete all -l app=*{app label}***

## GCloud
### Log in again
gcloud **auth login**

# Curl
### Curl request
curl http://35.222.147.178:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

### Curl request every few seconds
watch curl http://35.222.147.178:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

### Create configuration map with one entry
kubectl **create configmap currency-conversion --from-literal=*CURRENCY_EXCHANGE_URI=http://currency-exchange***

### List of configmaps
kubectl **get configmap**

### Get a specific configmap
kubectl **get configmap *{map name}***

### Get a configmap's yaml
kubectl **get configmap *{map name}* -o yaml**

### Get information about a specific deployment history (mostly show the amout of changes...)
kubectl **rollout history deployment *{deployment name}***

### Undo deployment
kubectl **rollout undo deployment *{deployment name}* --to-revision=1**

### Set autoscaling
kubectl **autoscale deployment *{deployment name}* --min=1 --max=10 --cpu-percent=70**

kubectl **autoscale deployment currency-exchange --min=1 --max=10 --cpu-percent=5**

### To see the usage of autoscaling (horizontal pode autoscaling)
kubectl **get hpa**

### To delete autoscaling
kubectl **delete hpa *{deployment name}***



## **NOTE** - we can write get pod | get pod**s**
