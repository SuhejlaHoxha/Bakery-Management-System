# Bakery Management System

## Overview
This web application is a comprehensive Bakery Management System that handles categories, products, and orders. It supports different user roles like Admin and User, each with specific authorities and tasks. This project is developed using Spring Boot, Angular, and several other technologies to ensure robust performance and secure authentication.

## Features
- Role Management: Different user roles (Admin and User) with specific authorities.
- Authentication and Authorization: Implemented using Spring Security and JWT tokens.
- Category, Product, and Order Management: Create, read, update, and delete (CRUD) operations for categories, products, and orders.
- RESTful API: For seamless communication between the client and server.
- Exception Handling: Centralized exception handling to manage errors efficiently.
- Transactions Management: Ensures data consistency and integrity.
- Front-End Integration: User interface created using Angular.

# Technologies Used
- **Back-End**:
    - Spring Boot
    - Spring Security (JWT)
    - Hibernate (ORM)
    - MySQL (Database)
    - JDBC
- **Front-End**:
    - Angular
- **Testing**:
    - Postman (API testing)
- **Programming Paradigm**:
    - Object-Oriented Programming (OOP)

# Prerequisites
Before you begin, ensure you have the following installed:
- Java Development Kit (JDK) 11 or higher
- Node.js and npm
- MySQL Server
- Maven

## Getting Started 
**Back-End Setup**
1. **Clone the repository:**
 ```
git clone https://github.com/yourusername/bakery-management-system.git
cd bakery-management-system
 ```
2. **Configure MySQL database:**

Create a database named cafe_management and update the database configuration in src/main/resources/application.properties
 ```
spring.datasource.url=jdbc:mysql://localhost:3306/bakery_management
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
 ```

3. **Build and run the application:**
 ```
mvn clean install
mvn spring-boot:run
 ```

**Front-End Setup**

1. **Navigate to the front-end directory:**

 ```cd frontend ```

2. **Install dependencies:**

 ```npm install ```

3. **Run the Angular application:**

 ```ng serve ```

The application will be available at  ```http://localhost:4200 ```.

## API Documentation
The RESTful API endpoints are documented using Swagger. Once the application is running, you can access the Swagger UI at:

 ```http://localhost:8081/swagger-ui/index.html ```

## Usage 
**User Roles and Permissions**
- **Admin:** Can manage categories, products, and orders.
- **User:** Can view and place orders.

## Testing
Use Postman to test the RESTful API endpoints. You can import the Postman collection from the postman directory.

---------------

**Implementing Flyway in the "Bakery Management System" Project**

*Description* 
Flyway is a database version control tool that allows us to manage changes to the database schema through migration scripts. 
*Why Flyway?* 
We chose Flyway because of its simplicity, wide database support, and easy integration with Spring Boot. 

*Configuration* 
To integrate Flyway into our Spring Boot project, we followed these steps: 

1) Adding Dependencies: First, we add the Flyway dependency to our project's pom.xml file. 
```
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

2) Configuring Properties: We configure the Flyway properties in the Spring Boot application.properties file. 
```
spring.flyway.enabled=true 
spring.flyway.baseline-on-migrate=true 
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=db_user 
spring.datasource.password=db_password 
```

3) *Migration Scripts:* Migration scripts are placed in the src/main/resources/db/migration directory. The naming convention for the scripts is V<VERSION>__<DESCRIPTION>.sql, where <VERSION> is the migration version number and <DESCRIPTION> is a brief description of the migration. 

*Usage:* 
Once we have configured and added the migration scripts, we simply run our application. Flyway will check the database to determine if any migrations are needed and will apply any pending migrations in the correct order.
