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

**Implementimi i Flyway në Projektin "Bakery Management System"**

*Përshkrim* <br>
Flyway është një mjet për menaxhimin e versioneve të bazave të të dhënave që na lejon të kontrollojmë ndryshimet në skemën e bazës së të dhënave përmes skriptave migrimi. <br><br>
*Pse Flyway?* <br>
Zgjodhëm Flyway për shkak të thjeshtësisë së tij, mbështetjes së gjerë të bazave të të dhënave dhe integrimit të lehtë me Spring Boot. <br>

*Konfigurimi* <br>
Për të integruar Flyway në projektin tonë Spring Boot, ne ndoqëm hapat e mëposhtëm: <br>

1) Shtimi i Varësive: Fillimisht, shtojmë varësinë e Flyway në skedarin 'pom.xml' të projektit tonë. <br>

<**dependency**> <br>
    <**groupId**>org.flywaydb</**groupId**> <br>
    <**artifactId**>flyway-core</**artifactId**> <br>
</**dependency**> <br>
 
2) Konfigurimi i Property-ve: Konfigurojmë propertitë e Flyway në skedarin application.properties të Spring Boot. <br>
spring.flyway.enabled=true <br>
spring.flyway.baseline-on-migrate=true <br>
spring.datasource.url=jdbc:mysql://localhost:3306/database_name <br>
spring.datasource.username=db_user <br>
spring.datasource.password=db_password <br>

3) Skriptat e Migrimit: Skriptat e migrimit vendosen në direktorinë src/main/resources/db/migration. Emërtimi i skriptave ndjek konvencionin V<**VERSION**>__<**DESCRIPTION**>.sql, ku <**VERSION**> është numri i versionit të migrimit dhe <**DESCRIPTION**> është një përshkrim i shkurtër i migrimit. <br>

*Përdorimi:* <br>
Pasi të kemi konfiguruar dhe shtuar skriptat e migrimit, thjesht ekzekutojmë aplikacionin tonë. Flyway do të kontrollojë bazën e të dhënave për të përcaktuar nëse ndonjë migrim është i nevojshëm dhe do të aplikojë çdo migrim të papërdorur në rendin e duhur.
