# Spring Boot Application - Library Management

This project acts as a library management system, where you can manage Users and Books, and also provides functionalites like borrowing a book and returning a book.

This project is built with Maven, and it uses h2 in-memory database. This databse using the proterties set in the application.properties file, you can instantiate a database that will exist in memory, or be written to any accessible file system. 
* To access the console for the database, we need to go to "http://localhost:8080/h2-console" on browser and log in. The username and password are stated in the application.properties.

Dependencies that are included in this project are: 
- Spring Web
- Spring Data JPA
- H2 Database
(other dependencies can be found in pom.xml)

## Requirements

For building and running the application we need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.6.3](https://maven.apache.org)

## Running the application locally

To run our Spring Boot application on our local machine, we can execute the `main` method in the `com.library.LibraryApplication.java` class from our IDE.
Alternatively we can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Features and Designs

- The Entites (Models) in this project are designed as User (USERS as table), Book as parent class of BookItem, BookItem (as an instance from Book) and BookRental (BOOK_RENTAL as table). I decided that the mapping between the User and the Book will be esablished through BookRental Entity, i.e there is:
  - @OneToOne mapping to User and
  - @OneToMany mapping to BookItems.
  
  Like this, we will have a separate Join Table (RENTED_BOOKS), where we will have the id from the Rental and the id from the Book. This keeps the data model clean, and easy to handle. (picture below)
  
  ![image](https://user-images.githubusercontent.com/18428966/188007728-1da8b8a2-cb66-48bb-a2c8-c1f92d192619.png)

- The Projects code is structured in different packages.
```
  src/main/java
    +- com
        +- library
            +- LibraryApplication.java
                +- controller
                |   +- BookController.java
                |   +- BookRentalController.java
                |   +- UserController.java
                +- exception
                |   +- BookCopyException.java
                |   +- BookLimitException.java
                +- model
                |   +- Book.java
                |   +- BookItem.java
                |   +- BookRental.java
                |   +- User.java
                +- repository
                |   +- BookRentalRepository.java
                |   +- BookRepository.java
                |   +- UserRepository.java
                +- service
                |   +- BookRentalService.java
                |   +- BookService.java
                |   +- UserService.java
                |   +- impl
                |   |   +- BookRentalServiceImpl.java
                |   |   +- BookServiceImpl.java
                |   |   +- UserServiceImpl.java
```
## Testing
- For running the Unit Test, we can do it through the IDE (I used Eclipse Version 2019-12 (4.14.0)) as Right Click on the project -> Run As -> JUnit Test.
- For Integrative Testing of the Application, i used [Postman API Platform](https://www.postman.com/). Below you will find example JSON Objects that can be used for creating the Users and Books.

- For Book:  
```
{
	"title":"Test Book 1",
	"author":"Test Author 1",
	"available":true
}
```
- For User:
```
{
	"displayName":"Test User 1",
	"username":"test_username",
	"email":"test.user1.email@test.com",
	"password": "*********",
	"totalBooks": "0"
}
```


## Contact
 - Martin Shterjoski: martin.sterjoski@gmail.com
