# Build images for the services
docker build -t  bootstrapservice services/BootStrapService/.

docker run --name bootstrapservice --rm --net=host bootstrapservice:latest



# Prerequisite:  if Kafka Stream Source connector is UP(say DB for CDC)
# create DB connector
curl -X POST -H "Content-Type: application/json" \
     --data @datasource-source-connector.json \
     http://localhost:8083/connectors
