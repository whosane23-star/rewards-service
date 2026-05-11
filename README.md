#The rest API to get customer rewards based on the customer Id
#Problem
#A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

The package name is structured as com.rewardsservice
Exception is thrown if customer does not exists or any kind of exception.
Added Swagger for API documentation.
http://localhost:8080/swagger-ui/index.html
#Request end point and response

http://localhost:8080/api/rewards/2
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
