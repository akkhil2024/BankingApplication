Entitites involved:
1. There are two users for the application: Admin User and Regular User(Customer)
  Role: ADMIN_USER,CUSTOMER
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

  ----------
   Data Model:
   -----------
   sinkDB:(Downstream DataSource)

  Tables:
   --------
   
   1. Target DataSource
   ----------------------
   account_balances:
-----------
-- sinkdb.account_balances definition

CREATE TABLE `account_balances` (
  `account` varchar(255) NOT NULL,
  `amount` decimal(19,4) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account`),
  KEY `fk_transaction_account_balances` (`account_id`),
  CONSTRAINT `fk_transaction_account_balances` FOREIGN KEY (`account_id`) REFERENCES `transaction` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




   2. Source DB(used for customer onboarding)

db name:

----------
Custodian DB:
-----------

	1. table: Project
	------------------
-- sinkdb.project definition


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


  2. custodian:
  -----------
  -- sinkdb.custodian definition

-- sinkdb.custodian definition

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
  


  3.  Client
  ---------
  -- sinkdb.client definition

CREATE TABLE `client` (
  `client_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
  
  4. transaction:
  ------------
  
  -- sinkdb.`transaction` definition

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


5. Customer:
-----------
-- sinkdb.customer definition

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


6. account_balances:
-----------
-- sinkdb.account_balances definition

CREATE TABLE `account_balances` (
  `account` varchar(255) NOT NULL,
  `amount` decimal(19,4) DEFAULT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`account`),
  KEY `fk_transaction_account_balances` (`account_id`),
  CONSTRAINT `fk_transaction_account_balances` FOREIGN KEY (`account_id`) REFERENCES `transaction` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-------
Sink DB:
-------

-- sinkdb.`transaction` definition

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
