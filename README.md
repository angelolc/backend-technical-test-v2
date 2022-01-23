## Summary
TUI DX Backend technical Test v2 made by **Angelo La Costa**

This is a Spring Boot Java Application with an embedded h2 db that exposes the following endpoints:
- **POST /api/v1/pilotes/order**: Creates a new pilotes order for a given client, already present in the system. The order is related to a client, identifiable via his username, and must have a quantity and the delivery address. This operation is public
- **PATCH /api/v1/pilotes/order**: Updates an already existing order, with new quantity and address. The update must be done within five minutes from the original order creation otherwise time out error will be returned. This operation is public
- **GET /api/v1/pilotes/orders**: Searches all the orders related to the clients whose data matches the given parameters. E.g.: All orders of clients whose name contains an 'a'. This is a secured operation so authentication is required first.

## Security 
In order to use the search orders operation authenticate via basic authentication with the following user:
- username: admin
- password: password

Password is encrypted using BCrypt algorithm.

## Architecture
MVC style architecture with the following layers:

- Controller: exposes the endpoints and handles request/response;
- Service: is responsible for the business logic;
- Repository: communicate with the db

## Database

Database console is accessible with the following configs:
- console url: http://localhost:8080/h2-console
- JDBC url: jdbc:h2:mem:pilotesdb
- username: sa

## Documentation
All the endpoints are documented with swagger at the following url:
- http://localhost:8080/swagger-ui-pilotes.html

The endpoints and api-docs can be used also with postman importing the JSON inside the folder:
- /postman

## Technologies
Java 11, Spring boot, Maven, h2, JPA, Spring Security, Lombok, Springdoc, Easymock, Docker

## Build
mvn clean install

## Run 
Command line:
- java -jar backend-technical-test-<VERSION>.jar

Docker file:
- docker run -p8080:8080 backend-technical-test:latest

Docker compose:
- docker-compose up --build

 


