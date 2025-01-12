
**Set Up**
from project root folder run:
 docker compose up -d

 This brings uo kafka container and other related components

**KAFKA SETUP:
**
docker-compose.yml is used to spwan the components for Kafka using the image: https://github.com/lensesio/fast-data-dev
<img width="213" alt="image" src="https://github.com/user-attachments/assets/1a8a7a5a-4607-40e3-af1b-f852b81af62e" />


Port tunneling from port: 3030
<img width="746" alt="image" src="https://github.com/user-attachments/assets/ddc4c829-25c9-4a62-a7f0-ba5c129f40ef" />

**Topologies:**
TransactionFilterTopology
  Data is read from transactions.csv to topic **transactions** which is parsed using filter topology to filter transactions of type transfer to **transactions-filter** and samae are written to **account-balances** topic which saves the transactions into inbuild state store of Kafka Streams


  Intermediate topics are created for the state Store for  combination of <application_ID> + <state_Store_name>
  	STATE_STORE = "account-balance-store"
   	APPLICATION_ID_CONFIG = "account-balance-calculation"

**Entitites involved:**
1. There are two users for the application: Admin User and Regular User(Customer)
  Role: ADMIN,Custodian,CUSTOMER
2. Transaction Request Parameter; 
	Entity that is read from Upstream sources using KakfaConnect.
	Transfered as Value to the downstream service and account database
3. Loan Request payload/Object;


Flow is as below:
--------------------
Scenario:
---------
  For Customer Onboarding and Updates (Using REST APIs)
	POST /api/users
	GET /api/users
	GET: /api/users/{userId}
	PUT: /api/users/{userId}
	Patch: /api/users/{userId}
	
	
	
Customer makes a call to API gateway; where the reuest is sent to Rate limiting service first; with time of 10 reuests /sec.
If successfully; the Request is forwared to Query Service (following CQRS Microservices pattern); 

Two scenarios:
1.1 When User/Customer is expected to be already present(Update, Delete, Patch,Put Operations)
that calls the Redis cache to see id User entity is there
(Read throgh cache); if missed go to call the Authentication service(an Upstream Microservice/Spring Boot Application); that based on ROle based Authentication
(RBAC) looks for User in Elastic search and returns the User Role and Permission assigned to User as part of response that is further composed by
AuthenticationService as part of JWT Token claims. 

1.2. When Ever new Customer is created by Admin; The API call is made to Command Service(following CQRS Microservices pattern) with POST Operation.
 The User is inserted into the RedisCache and also update in ES and saved into Database partitioned by Client/Tenant.
 
Scenario:
---------
Ingestion of the transactions from multiple sources using Kafka broker
 
2.
2.1 Admin need to start a data pipeline that ingests data from many Source systems;
   as; Local File System
       Fetch data on change on any upstream Database; for any change in row
       Fetch from exposed 3rd part REST endpoint.
   All anove are extendable to many other source using corresponding Connectors defined in Kakfa Connect.
   
   The Centralized service called; BootStrapService bringup the KafkaConnect, Kafka broker and create  topics and bring up Schema registry.
   
   there are following services(individual Spring boot applications) involved in data pipeline:
     1. TransactionConsumer; that acts as Consumer to read transactions and as producer to write the data to CalculatorService that write and updates the
	   state store (inbuild Kafka Datastore) and do business logic calculation to evaluate id loan can be granted.



  Tables:
   --------
   
   1. Target DataSource
   ----------------------
   account_balances:
-----------
 ```
 CREATE TABLE `account_balances` (
  `account` varchar(255) NOT NULL,
  `amount` decimal(19,4) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account`),
  KEY `fk_transaction_account_balances` (`account_id`),
  CONSTRAINT `fk_transaction_account_balances` FOREIGN KEY (`account_id`) REFERENCES `transaction` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

   2. Source DB(used for customer onboarding)

db name:

----------
Custodian DB: (Database for Source System that can create transactions itself OR can pull data using Kafka Connect)
-----------

	1. table: Project (Logical Context to define some documents being assosicted with transaction)
	------------------
 ```
	CREATE TABLE `project` (
  `project_id` char(36) NOT NULL,
  `custodian_id` char(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`project_id`,`custodian_id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_transaction_date` (`created_date`),
  KEY `fk_client_id_project` (`client_id`),
  KEY `fk_custodian_id_project` (`custodian_id`),
  CONSTRAINT `fk_client_id_project` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `fk_custodian_id_project` FOREIGN KEY (`custodian_id`) REFERENCES `custodian` (`custodian_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

  2. custodian:
  -----------
 ```
CREATE TABLE `custodian` (
  `custodian_id` char(36) NOT NULL,
  `client_id` bigint NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `role` enum('Admin','Customer') NOT NULL DEFAULT 'Customer',
  `account_id` varchar(255) NOT NULL,
  PRIMARY KEY (`custodian_id`),
  KEY `idx_custodian_id` (`custodian_id`),
  KEY `idx_transaction_date` (`created_date`),
  KEY `fk_client_id` (`client_id`),
  CONSTRAINT `fk_client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

 ```

  3.  Client
  ---------
 ```
 CREATE TABLE `client` (
  `client_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

  
  4. transaction:
  ------------
  
 ```
	CREATE TABLE `transaction` (
  `transaction_id` char(36) NOT NULL,
  `account_id` bigint NOT NULL,
  `customer_id` char(36) NOT NULL,
  `ammount` decimal(15,2) NOT NULL,
  `client_id` bigint NOT NULL,
  `transaction_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `status` enum('Pending','completed','failed','refunded') NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`transaction_id`),
  KEY `idx_axxount_id` (`account_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_transaction_date` (`transaction_date`),
  KEY `fk_client_id_transaction` (`client_id`),
  CONSTRAINT `fk_client_id_transaction` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `fk_customer_transaction` FOREIGN KEY (`customer_id`) REFERENCES `custodian` (`custodian_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


 ```


5. Customer:
-----------
```
CREATE TABLE `customer` (
  `customer_id` char(36) NOT NULL,
  `client_id` bigint NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  `status` enum('Pending','completed','failed','refunded') NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`customer_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_transaction_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```

6. account_balances:
-----------
```

CREATE TABLE `account_balances` (
  `account` varchar(255) NOT NULL,
  `amount` decimal(19,4) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account`),
  KEY `fk_transaction_account_balances` (`account_id`),
  CONSTRAINT `fk_transaction_account_balances` FOREIGN KEY (`account_id`) REFERENCES `transaction` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```


7. Intermediate table to have data populated to be sent as Payload corresponding to Avro Schema:
   
```
	CREATE TABLE Bank_Transactions (
    transaction_id     UUID PRIMARY KEY,           -- Unique identifier for the transaction
    account_id         UUID NOT NULL,              -- ID of the account involved in the transaction
    tenant_id          UUID NOT NULL,              -- ID of the tenant (client) owning the account
    transaction_type   VARCHAR(20) NOT NULL,       -- Type: CREDIT, DEBIT, TRANSFER, etc.
    amount             DECIMAL(15, 2) NOT NULL,    -- Transaction amount with two decimal places
    currency           CHAR(3) NOT NULL,           -- ISO currency code (e.g., USD, EUR)
    transaction_date   TIMESTAMP NOT NULL,         -- Date and time of the transaction
    status             VARCHAR(20) NOT NULL,       -- Status: PENDING, SUCCESS, FAILED
    description        TEXT,                       -- Optional description of the transaction
    created_at         TIMESTAMP DEFAULT NOW(),    -- When the transaction was recorded
    updated_at         TIMESTAMP DEFAULT NOW()     -- Last updated time (for status changes, etc.)
);

```
-------
Sink DB:(State Store ; a persistent Store used to process events as Streams)
-------

```

CREATE TABLE Bank_Transactions (
    transaction_id     UUID PRIMARY KEY,           -- Unique identifier for the transaction
    account_id         UUID NOT NULL,              -- ID of the account involved in the transaction
    tenant_id          UUID NOT NULL,              -- ID of the tenant (client) owning the account
    transaction_type   VARCHAR(20) NOT NULL,       -- Type: CREDIT, DEBIT, TRANSFER, etc.
    amount             DECIMAL(15, 2) NOT NULL,    -- Transaction amount with two decimal places
    currency           CHAR(3) NOT NULL,           -- ISO currency code (e.g., USD, EUR)
    transaction_date   TIMESTAMP NOT NULL,         -- Date and time of the transaction
    status             VARCHAR(20) NOT NULL,       -- Status: PENDING, SUCCESS, FAILED
    description        TEXT,                       -- Optional description of the transaction
    created_at         TIMESTAMP DEFAULT NOW(),    -- When the transaction was recorded
    updated_at         TIMESTAMP DEFAULT NOW()     -- Last updated time (for status changes, etc.)
);


```
<img width="434" alt="image" src="https://github.com/user-attachments/assets/ac0d68e9-2233-4ab0-a1c4-c1c9264bc2cd" />



<img width="314" alt="image" src="https://github.com/user-attachments/assets/308b2c0c-4ce4-4832-9144-d2c9250250cd" />



<img width="320" alt="image" src="https://github.com/user-attachments/assets/09a12bd7-d408-4910-982e-5f9b71acb814" />

![BankingProject_Final](https://github.com/user-attachments/assets/f9b67504-9c79-426a-b4db-e9d36cfa29a2)


AVRO Schema to be used:**( This is the Schema to be used to send the Transaction payload)**
**For Transaction Payload:**

**THIS TRANSACTION IS USED AS EVENT**
```
	{
  "type": "record",
  "name": "BankTransaction",
  "namespace": "com.bank.transactions",
  "fields": [
    {
      "name": "transaction_id",
      "type": "string"
    },
    {
      "name": "account_id",
      "type": "string"
    },
    {
      "name": "tenant_id",
      "type": "string"
    },
    {
      "name": "transaction_type",
      "type": {
        "type": "enum",
        "name": "TransactionType",
        "symbols": ["CREDIT", "DEBIT", "TRANSFER", "REVERSAL"]
      }
    },
    {
      "name": "amount",
      "type": "double"
    },
    {
      "name": "currency",
      "type": "string"
    },
    {
      "name": "transaction_date",
      "type": {
        "type": "long",
        "logicalType": "timestamp-millis"
      }
    },
    {
      "name": "status",
      "type": {
        "type": "enum",
        "name": "TransactionStatus",
        "symbols": ["PENDING", "SUCCESS", "FAILED"]
      }
    },
    {
      "name": "description",
      "type": ["null", "string"],
      "default": null
    },
    {
      "name": "created_at",
      "type": {
        "type": "long",
        "logicalType": "timestamp-millis"
      }
    },
    {
      "name": "updated_at",
      "type": {
        "type": "long",
        "logicalType": "timestamp-millis"
      }
    }
  ]
}

```


Complete ER Model

![Custodian_ER_Model drawio](https://github.com/user-attachments/assets/66a68bb6-5858-42a6-b897-b9e24d0b06fa)





==============

TO DO:
1. define generic serdes for  all data types...
2. Define Versioning for per tenant/ client using Avro Schema Registry


