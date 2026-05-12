# Rewards Service API

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

Customers earn:
- **2 points** for every dollar spent over **$100** in a transaction.
- **1 point** for every dollar spent between **$50 and $100** in a transaction.

## Example

For a purchase of **$120**:

- 2 × $20 = 40 points
- 1 × $50 = 50 points

**Total Reward Points = 90**

This application calculates the reward points earned by each customer monthly and overall for a three-month period.

---

# Tech Stack

- Java 8
- Spring Boot
- Gradle
- REST API
- Swagger / OpenAPI
- JUnit 5
- Mockito

---

# Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.rewardsservice
│   │       ├── controller
│   │       ├── service
│   │       ├── model
│   │       ├── exception
│   │       └── config
│   └── resources
│       └── application.yml
│
└── test
    └── java
        └── com.rewardsservice
```

---

# Features

- Reward points calculation based on transactions
- Monthly rewards summary
- Total rewards summary
- RESTful API implementation
- Global exception handling
- Swagger API documentation
- Unit testing with Mockito and JUnit
- Clean layered architecture

---


---

# API Endpoint

## Get Rewards by Customer ID

### Request

```http
GET /api/rewards/{customerId}
```

### Example

```text
http://localhost:8080/api/rewards/2
```

---

# Sample Response

```json
{
  "customerId": 2,
  "customerName": "John",
  "monthlyRewards": {
    "JANUARY": 40,
    "FEBRUARY": 110
  },
  "totalRewards": 150,
  "message": "Transaction record found",
  "status": "Success"
}
```

---

# Exception Handling

The application uses `@RestControllerAdvice` for global exception handling.

## Handled Exceptions

- Customer not found exception
- Invalid request exception
- Internal server exception

## Sample Error Response

```json
{
  "message": "Customer not found",
  "status": "FAILED"
}
```

---

# Swagger API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# How to Run the Project

## Clone the Repository

```bash
git clone <repository-url>
```

## Navigate to Project Directory

```bash
cd rewards-service
```

## Build the Project

```bash
./gradlew build
```

## Run the Application

```bash
./gradlew bootRun
```

The application will start on:

```text
http://localhost:8080
```

---

# Running Test Cases

Run unit test cases using:

```bash
./gradlew test
```

---

# Test Coverage

The project includes:

- Service layer test cases
- Controller layer test cases
- Exception handling test cases
- Mockito-based unit testing

---


# Author

Ashraf
