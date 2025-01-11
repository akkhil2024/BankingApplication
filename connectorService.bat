curl -X POST -H "Content-Type: application/json" --data @datasource-source-connector.json http://localhost:8083/connectors

curl -X POST -H "Content-Type: application/json" --data @postgres-datasource-source-connector.json http://localhost:8083/connectors

curl -X POST -H "Content-Type: application/json" --data @account-balances-mysql-sink-connector.json http://localhost:8083/connectors