# Book store

A book store back-end Java Project, using Spring Framework to provide web functionalities to server side.
	
## Technologies used:
- Java 1.8
- Junit 5
- Lombok
- Spring Boot
- Spring MVC
- Spring Data JPA (MongoDB)
- Spring Security
- JWT
- Maven

## Installation
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies. The default server port is 8090, but you can change it easyless through apllication.properties file.

## How to configure database
The mongoDB creates a database and collections needed at first request, then you only need start your mongoDB and config your credentials to application.properties file.

```
#datasource
spring.data.mongodb.database=book-store
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.uri=mongodb://localhost/book-store
spring.data.mongodb.username=
spring.data.mongodb.password=

#server config
server.port=8090
server.servlet.context-path=/book-store
```

## How to use the API
You need start mongoDB before to start api. You can run `mongod` in your terminal to start database, then, you run the project through the your preferred IDE and head out to [http://localhost:8080/book-store](http://localhost:8080/book-store)

## REST API Structure

The app defines following REST structure for all endpoints.

    Add new resource
    POST /resource
    
    Get all resources
    GET /resource
    
    Get resource by id
    GET /resource/{id}
    
    Update resource by id
    PUT /resource/{id}
    
    Delete resource by id
    DELETE /resource/{id}

You can test them using postman or any other rest client.
