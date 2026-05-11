# Rewards Service API

A Spring Boot REST API to calculate customer reward points based on purchase transactions.

---

## Problem Statement

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

### Rewards Calculation Rules

- 2 points for every dollar spent over `$100`
- 1 point for every dollar spent between `$50` and `$100`

### Example

For a purchase of `$120`:

- 2 × 20 = 40 points
- 1 × 50 = 50 points

Total Reward Points = **90**

The application calculates:

- Monthly reward points
- Total reward points
- Rewards for each customer during a three-month period

---

## Tech Stack

- Java 17
- Spring Boot
- Gradle
- REST API
- Swagger / OpenAPI

---

## Package Structure

```text
com.rewardsservice
```

---

## Features

- Calculate reward points based on transactions
- Monthly rewards summary
- Total rewards summary
- Exception handling
- Swagger API documentation
- RESTful API implementation

---

## Exception Handling

The application throws exceptions when:

- Customer does not exist
- Invalid request is provided
- Any unexpected server error occurs

---

## Swagger API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## API Endpoint

### Get Rewards By Customer ID

```http
GET /api/rewards/{customerId}
```

### Example Request

```http
GET http://localhost:8080/api/rewards/2
```

---

## Sample Response

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

## Running the Application

### Clone Repository

```bash
git clone <repository-url>
```

### Navigate to Project

```bash
cd rewards-service
```

### Run the Application

```bash
./gradlew bootRun
```

Application will start at:

```text
http://localhost:8080
```

---

## Author

Developed using Spring Boot REST API best practices.
