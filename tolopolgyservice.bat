# Build images for the services



docker build -t  tolopolgyservice services/Tolopolgyservice/.

docker run --name tolopolgyservice --rm --net=host tolopolgyservice:latest
