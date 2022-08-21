# Paths:

## /account
1. **GET:** returns user account balance

## /transfers
1. **GET:** returns all transfers sent or received by user
2. **POST:** send money to specified user
    body: json for request
    responses: 
        successful : 200
        unsucessful : 400

## /transfers/{id}
1. **GET:** returns transfer details for specified transfer ID
