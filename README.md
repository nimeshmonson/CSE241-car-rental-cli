# Hurts Rent A Lemon CLI Application
This is my final project to my database systems class. The project is to design and develop a database management application that keeps track of a fictional rental car company. The program uses SQL and JDBC to communicate with the database. 

## Usage
On logging in, the program prompts the user to choose if the user is a customer or an administrative staff
- If the user is a Customer, the program asks if a user is an existing customer or a new customer.
- If the user is new, the customer is asked to sign up and is added to the database
- The customer is then redirected to the customer menu, where they could make new rental, see existing rental, edit rental,
finish their rental and delete rental. The Customer_id and Customer_name is stored locally to be used in different places.
- If it is an existing user, the user is asked to login with the cust_id and name. The customer_id and Customer_name is stored
locally to be used in different places
- Then the user is taken to the customer menu mentioned above.

## Incompletions
- Issues with converting date type
- Could not finish the administrative interface
- There is an sql error when getting the vehicle list at a location

