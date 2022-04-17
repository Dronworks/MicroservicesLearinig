## **NOTE** - in kubernates console shift + page up / down to see more lines
# Kuberneter works via -
 single responsibility principle - single concept = single responsibility

## create deployement
creates - deployement, replicas and pod

## expose deplotement
creates - service

# Pods
- pod- smalles deployable unit
- container lives "inside" a pod
- pod can have number of containers who are sharing resources, they can talk to each other using localhost
- pods can consist of single application containers or they can be across multiple pods
- **namespaces** - isolations for parts of a cluster from other parts. *Exampale*: QA and DEV inside a cluster how to separate dev and qa resources? Can create namespace for QA and DEV and assosiate resources with each of them.
- **labels** - pod, replicaset, deployement, service - to link this together we use selectors and labels. Lable really important to link pod with replicaset and service.
- **annotations**- metainfo about a specific pod- release id, build id, author name
- **status**- runnig for example
- pod creates a **way to keep our containers running**, gives containers an ip address, provides categorization to the containers by providing labels
- pods running on nodes

