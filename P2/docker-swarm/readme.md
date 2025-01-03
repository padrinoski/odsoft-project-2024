# Docker Swarm Guide

## Initialize Docker Swarm

```sh
docker swarm init
```

Join additional worker nodes:

```sh
docker swarm join --token <TOKEN> <MANAGER-IP>:2377
```
Verify the cluster:

```sh
docker node ls
```

Deploy Your Services as a Stack
Use the updated Compose file for each set of services to deploy them in Swarm.

```sh
docker stack deploy --compose-file boot-services.yml boot-services
docker stack deploy --compose-file books-query.yml books-query
```

Verify running stacks:

```sh
docker stack ls
docker stack services boot-services
```

Monitor and Manage Services
Use Swarm tools to monitor and manage your deployments:

Check service logs:
```sh
docker service logs <SERVICE_NAME>
```

Scale services dynamically:
```sh
docker service scale <SERVICE_NAME>=<NUM_REPLICAS>
```

Automate CI/CD Integration
Build and push images in your CI pipeline to a container registry (e.g., Docker Hub, AWS ECR).
Automate stack deployments using Swarm commands after successful builds:
```sh
docker stack deploy --compose-file <file> <stack_name>
```
