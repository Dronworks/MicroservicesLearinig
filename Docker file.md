# File structure

### FROM
This will start the creation from a base image.
* Example: `FROM ubuntu`

### RUN
This will run commands inside the docker during build.
* Example: `RUN apt-get update`
* Example: `RUN pip install flask`

### COPY 
This will copy any files to the docker.
* Example: `COPY /home/andreyd/files /opt/files`

### ENTRYPOINT
This will start any application after docker is up.
* Example: `ENTRYPOINT FLASK_APP=/opt/app.py flask run --host=0.0.0.0`
* Example: `ENTRYPOINT ["python", "app.py"]`

# Build commands - the new image will appear in docker images

### Docker build command
`docker build [Dockerfile location]`

Where Dockerfile location can be in the same directory and be replaced with `.`

# Login
To push images we need to login to account
* `docker login`

### Docker build command with name (tag) 
* Tag is important to push because your account name should be provided
* `docker build . -t my-app`
* `docker build . -t andreyd/my-app`

# Push command
To push the image to docker repository
* `docker push [image name]`