
docker exec -t kafka kafka-topics --bootstrap-server localhost:9092 --create --topic transactions --partitions 1 --replication-factor 1

docker exec -t kafka kafka-topics --bootstrap-server localhost:9092 --create --topic transfer-transactions --partitions 1 --replication-factor 1

docker exec -t kafka kafka-topics --bootstrap-server localhost:9092 --create --topic account-balances --partitions 1 --replication-factor 1

docker exec -t kafka kafka-topics --bootstrap-server localhost:9092 --create --topic loan-requests --partitions 1 --replication-factor 1

docker exec -t kafka kafka-topics --bootstrap-server localhost:9092 --create --topic loan-evaluation-response --partitions 1 --replication-factor 1

