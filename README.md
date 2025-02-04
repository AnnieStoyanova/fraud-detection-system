# 🔍  Fraud Detection System API 

## 📌 Overview 

This is a real-time fraud detection system that processes financial transactions using a Kafka-based data pipeline. Transactions are received via a REST API, validated for fraud, and stored in PostgreSQL. 

## ⚙️ Architecture Design 

The system follows a microservices-based architecture: 

- Transaction Generator 

Simulates real-time financial transactions. 

Sends transactions to the REST API. 

- Fraud Detection API (Spring Boot) 🏦 

Receives transactions via REST API. 

Publishes transactions to Kafka. 

Consumes transactions from Kafka. 

Checks for fraud. 

Stores non-fraudulent transactions in PostgreSQL. 

- Kafka (Message Queue) 📬 

- Zookeeper manages Kafka brokers. 

Kafka topics store transactions before processing. 

- PostgreSQL (Database) 🗄️ 

Stores verified transactions. 

Allows querying of stored transactions. 

## 🛠️ Technologies Used 

**Spring Boot (Java) – API Development**

**Kafka – Real-time messaging** 

**Zookeeper – Kafka cluster management** 

**PostgreSQL – Database** 

**Docker & Docker Compose – Containerization** 

 

## 🔥 Running the API in Docker 

### Step 1: Clone the Repository 

`git clone https://github.com/your-repo/fraud-detection-system.git `
`cd fraud-detection-system` 

### Step 2: Start the Entire System with Docker Compose 

`docker-compose up -d --build` 

#### ✅ This will start: 

Kafka & Zookeeper 

PostgreSQL 

Fraud Detection API 

### Step 3: Verify That Everything Is Running 

Run the following command to check running containers: 

`docker ps` 

✅ Expected Output: 

`CONTAINER ID   IMAGE                       STATUS       PORTS                   NAMES 
f8e1b2c3d4e5   fraud-detection-api         Up          0.0.0.0:8080->8081/tcp   bank_transactions_api 
c9d2e3f4a5b6   confluentinc/cp-kafka       Up          0.0.0.0:9092->9092/tcp   kafka 
a7b8c9d0e1f2   confluentinc/cp-zookeeper  Up          2181/tcp                 zookeeper 
g5h6i7j8k9l0   postgres:13                 Up          0.0.0.0:5432->5432/tcp   bank_transactions_db` 

### Step 4: Send Transactions Using the Generator 

#### The Transaction Generator is run outside Docker: 

`private static final String TRANSACTION_API_URL = "http://localhost:8080/api/transactions/add";`  

### Step 5: Check Logs to See Transactions 

View API logs: 

`docker logs -f bank_transactions_api` 

View Kafka logs: 

`docker logs -f kafka` 

### Step 6: Query Stored Transactions in PostgreSQL 

Connect to the database: 

`docker exec -it bank_transactions_db psql -U postgres -d bankSystem` 

Run SQL queries: 

To see 20 of saved transactions:
`SELECT * FROM transactions LIMIT 20;`

To see all non fraudulent saved transactions
`SELECT COUNT(*) FROM transactions;`

Exit PostgreSQL: 

`\q` 

 

### 📌 Additional Commands 

Stop and Remove All Containers 

`docker-compose down` 

Rebuild and Restart the Containers 

`docker-compose up -d --build` 

Check Logs of a Specific Container 

`docker logs -f bank_transactions_api` 

List Running Containers 

`docker ps` 

Enter PostgreSQL Database Inside the Container 

`docker exec -it bank_transactions_db psql -U postgres -d bankSystem` 

 

### 🎯 Summary 

✅ Transaction Generator sends data to API.
✅ API publishes transactions to Kafka.
✅ Kafka streams transactions for processing. 
✅ API consumes transactions, checks fraud, and stores them in PostgreSQL. 
✅ Data can be queried from PostgreSQL. 

 
