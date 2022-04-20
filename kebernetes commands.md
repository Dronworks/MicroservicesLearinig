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

### Apply new yaml
kubectl **apply -f *{yaml name}***


## GCloud
### Log in again
gcloud **auth login**

# Curl
### Curl request
curl http://35.222.147.178:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

### Curl request every few seconds
watch curl http://35.222.147.178:8100/currency-conversion-feign/from/USD/to/INR/quantity/10


## **NOTE** - we can write get pod | get pod**s**




kubectl autoscale deployment hello-world-rest-api --max=10 --cpu-percent=70
kubectl edit deployment hello-world-rest-api #minReadySeconds: 15

 
gcloud container clusters get-credentials in28minutes-cluster --zone us-central1-a --project solid-course-258105
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST

kubectl get events --sort-by=.metadata.creationTimestamp
kubectl get pods --all-namespaces
 


 

kubectl get pods
kubectl get replicaset
kubectl get events
kubectl get events --sort.by=.metadata.creationTimestamp
 
kubectl get rs
kubectl get rs -o wide
kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST
kubectl get rs -o wide
kubectl get pods
kubectl describe pod hello-world-rest-api-85995ddd5c-msjsm
kubectl get events --sort-by=.metadata.creationTimestamp
 
kubectl get events --sort-by=.metadata.creationTimestamp

 
gcloud container clusters get-credentials in28minutes-cluster --zone us-central1-c --project solid-course-258105
docker login
docker push in28min/mmv2-currency-exchange-service:0.0.11-SNAPSHOT
docker push in28min/mmv2-currency-conversion-service:0.0.11-SNAPSHOT
 
kubectl create deployment currency-exchange --image=in28min/mmv2-currency-exchange-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000

 
kubectl create deployment currency-conversion --image=in28min/mmv2-currency-conversion-service:0.0.11-SNAPSHOT
kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100
  
kubectl get deployments
 
kubectl get deployment currency-exchange -o yaml >> deployment.yaml 
kubectl get service currency-exchange -o yaml >> service.yaml 
 

 
kubectl delete all -l app=currency-exchange
kubectl delete all -l app=currency-conversion
 
kubectl rollout history deployment currency-conversion
kubectl rollout history deployment currency-exchange
kubectl rollout undo deployment currency-exchange --to-revision=1
 
kubectl logs currency-exchange-9fc6f979b-2gmn8
kubectl logs -f currency-exchange-9fc6f979b-2gmn8 
 
kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=5 
kubectl get hpa
 
kubectl top pod
kubectl top nodes
kubectl get hpa
kubectl delete hpa currency-exchange
 
kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
kubectl get configmap
 
kubectl get configmap currency-conversion -o yaml >> configmap.yaml
 
watch -n 0.1 curl http://34.66.241.150:8100/currency-conversion-feign/from/USD/to/INR/quantity/10
 
docker push in28min/mmv2-currency-conversion-service:0.0.12-SNAPSHOT
docker push in28min/mmv2-currency-exchange-service:0.0.12-SNAPSHOT
