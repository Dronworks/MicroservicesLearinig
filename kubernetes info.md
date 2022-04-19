## **NOTE** - we can see events to see what happened after each command - kubectl get events
## **NOTE** - in kubernates console shift + page up / down to see more lines
# Kuberneter works via -
 single responsibility principle - single concept = single responsibility

## create deployement
creates - deployement, replicas and pod

## expose deplotement
creates - service

## **knows** how to work with different technology **Load Balancers**

# Pods - rapper for a set of containers with an ip
- pod- smalles deployable unit
- container lives "inside" a pod
- pod is a thoughway unit. It can be deleted at any time and new one can be created at any time.
- pod can have number of containers who are sharing resources, they can talk to each other using localhost
- pods can consist of single application containers or they can be across multiple pods
- **namespaces** - isolations for parts of a cluster from other parts. *Exampale*: QA and DEV inside a cluster how to separate dev and qa resources? Can create namespace for QA and DEV and assosiate resources with each of them.
- **labels** - pod, replicaset, deployement, service - to link this together we use selectors and labels. Lable really important to link pod with replicaset and service.
- **annotations**- metainfo about a specific pod- release id, build id, author name
- **status**- runnig for example
- pod creates a **way to keep our containers running**, gives containers an ip address, provides categorization to the containers by providing labels
- pods running on nodes

# ReplicaSet
- a specific number of pods that are running at all times
- if you kill(kubectl delete) a pod the replicaset will create another instead. It is always monitoring the pods, and if there are lesser pods that is needed, it creates new pod. **NOTE NEW ip** will be assigned to the new pod.
- we can add more pods by using the scale command
- we can see what images a RS supports by running the *get rs -o wide*
- add another version *kubectl set image deployement* {deployment name} {container name}={image name} **---->** this command will create new replicaset which will create one pod and try to launch it.
- for each deployement (or version) there will be created a new replicaset to manage it
- **DEPLOYMENT** insures that an update happens without a hitch
- Deploying a "bad - non existing" image. How to debug? **kubectl get pods** will show us something like 
```
NAME                                    READY   STATUS             RESTARTS   AGE
hello-world-rest-api-687d9c7bc7-6z26w   1/1     Running            0          10h
hello-world-rest-api-687d9c7bc7-qrqd9   1/1     Running            0          10h
hello-world-rest-api-687d9c7bc7-x59rm   1/1     Running            0          10h
hello-world-rest-api-84d8799896-fxdx6   0/1     InvalidImageName   0          3m36s
```
**kubectl describe pod {pod id}** will show us something like this
```
 
Name:         hello-world-rest-api-84d8799896-fxdx6
Namespace:    default
Priority:     0
Node:         gke-dronworks-cluster-default-pool-c903a323-lp47/10.128.0.4
Start Time:   Mon, 18 Apr 2022 04:53:56 +0000
Labels:       app=hello-world-rest-api
              pod-template-hash=84d8799896
Annotations:  <none>
Status:       Pending
deployment.apps/hello-world-rest-api image updated
andrejy601@cloudshell:~$ kubectl get rs -o wide
NAME                              DESIRED   CURRENT   READY   AGE    CONTAINERS             IMAGES                                       SELECTOR
hello-world-rest-api-687d9c7bc7   3         3         3       23h    hello-world-rest-api   in28min/hello-world-rest-api:0.0.1.RELEASE   app=hello-world-rest-api,pod-template-hash=687d9c7bc7
hello-world-rest-api-84d8799896   1         1         0       105s   hello-world-rest-api   DUMMY_IMAGE:TEST                             app=hello-world-rest-api,pod-template-hash=84d8799896
andrejy601@cloudshell:~$ kubectl get rs
NAME                              DESIRED   CURRENT   READY   AGE
hello-world-rest-api-687d9c7bc7   3         3         3       23h
hello-world-rest-api-84d8799896   1         1         0       113s
andrejy601@cloudshell:~$ kubectl get pods
NAME                                    READY   STATUS             RESTARTS   AGE
hello-world-rest-api-687d9c7bc7-6z26w   1/1     Running            0          10h
hello-world-rest-api-687d9c7bc7-qrqd9   1/1     Running            0          10h
hello-world-rest-api-687d9c7bc7-x59rm   1/1     Running            0          10h
hello-world-rest-api-84d8799896-fxdx6   0/1     InvalidImageName   0          3m36s
andrejy601@cloudshell:~$ kubectl describe pod ^C
andrejy601@cloudshell:~$ hello-world-rest-api-84d8799896-fxdx6^C
andrejy601@cloudshell:~$ kubecrl describe pod hello-world-rest-api-84d8799896-fxdx6
-bash: kubecrl: command not found
andrejy601@cloudshell:~$ kubectl describe pod hello-world-rest-api-84d8799896-fxdx6                                                                                         
Name:         hello-world-rest-api-84d8799896-fxdx6
Namespace:    default
Priority:     0
Node:         gke-dronworks-cluster-default-pool-c903a323-lp47/10.128.0.4
Start Time:   Mon, 18 Apr 2022 04:53:56 +0000
Labels:       app=hello-world-rest-api
              pod-template-hash=84d8799896
Annotations:  <none>
Status:       Pending
IP:           10.72.2.7
IPs:
  IP:           10.72.2.7
Controlled By:  ReplicaSet/hello-world-rest-api-84d8799896
Containers:
  hello-world-rest-api:
    Container ID:
    Image:          DUMMY_IMAGE:TEST
    Image ID:
    Port:           <none>
    Host Port:      <none>
    State:          Waiting
      Reason:       InvalidImageName
    Ready:          False
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-mz8nj (ro)
Conditions:
  Type              Status
  Initialized       True
  Ready             False
  ContainersReady   False
  PodScheduled      True
Volumes:
  kube-api-access-mz8nj:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type     Reason         Age                     From               Message
  ----     ------         ----                    ----               -------
  Normal   Scheduled      5m59s                   default-scheduler  Successfully assigned default/hello-world-rest-api-84d8799896-fxdx6 to gke-dronworks-cluster-default-pool-c903a323-lp47
  Warning  Failed         3m48s (x12 over 5m59s)  kubelet            Error: InvalidImageName
  Warning  InspectFailed  48s (x26 over 5m59s)    kubelet            Failed to apply default image tag "DUMMY_IMAGE:TEST": couldn't parse image reference "DUMMY_IMAGE:TEST": invalid reference format: repository name must be lowercase
  ```
  Also we can see the events leading to this **kubectl get events --sort-by=.metadata.creationTimestamp**

- agter a scuccessfull newer version of a replica it will add pods with a good image and scale down previous image. 1 -> 3 | 2 -> 2 | 3 -> 1 | 3 -> 0 ("ROLLING UPDATE STRATEGY")

# Service
- created with expose command, gets an ip address when created, not changable!
- pods can have different ips, also in the middle a pod can die and new pod with a new ip appears. But users will always see the ip of the service and it will know what pod to access.
- we can see in the google cloud console - we can search load balancer and we will see that we have one for the service which is looking on pods ips
- kubectl get service will show
```
NAME                   TYPE           CLUSTER-IP    EXTERNAL-IP    PORT(S)          AGE
hello-world-rest-api   LoadBalancer   10.76.2.184   34.72.24.180   8080:30107/TCP   24h
kubernetes             ClusterIP      10.76.0.1     <none>         443/TCP          39h
```
we can see that kubernates acts as clusterIp

# GCS
- we can do all the scale work and replicaset actions via kubernetes engine -> workloads
- in revision history we can see the deployement hostory

# Master Node
- ETCD (Distributed Database) - saves the state of the cluster (how many instances, etc...)
- Kube api server (API-Server) - all the work with kubernetes including console.
- kube-shceduler (Scheduler) - ports <-> nodes
- kube-controller-manager (Controller Manager) - makes sure that actual state of kubernetes cluster equals the desired state. 

# Worker Nodes
- PODS (Multiple pods running containers) - several pods of a single worker node.
- Kublet (Node agent) - monitoring what happens on the node and communicates to the master node (Controller Manager).
- Kube proxy (Networking Component) - exposing services in pods and nodes.
- CRI - docker, rkt etc.. (Container Runtime) - making the containers run inside pods
