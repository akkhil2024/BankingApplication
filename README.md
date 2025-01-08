# BankingApplication
BankingApplication using Kafka Stream and Rest API


Entitites involved:
1. there are two users for the application: Admin User and Regular User(Customer)
2. Transaction DTO; that is transferes as Value to the downstream services and Database
3. Loan Request payload/Object


Flow is as below:
--------------------
Scenario:
---------
  For Customer Onboarding and Updates (Using REST APIs)

Customer makes a call to API gateway; where the reuest is sent to Rate limiting service first; with time of 10 reuests /sec.
If successfully; the Reueest is forwared to Query Service (following CQRS Microservices pattern); 

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
