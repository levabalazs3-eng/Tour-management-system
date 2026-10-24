Tour Management System

A multi-module Java application for managing travel tours and reviews. It covers raw JDBC, Java EE Servlets, and a modern Spring Boot REST API with JPA/Hibernate

Modules

- spring: Spring Boot REST API (Tours, Reviews, DTO mapping, JPA/Hibernate persistence)
- backend: Core domain logic and storage implementations (JDBC DAO, in-memory storage)
- desktop: Swing GUI client (TourFrame) for the core services
- web: Servlet and Filter setup (AuthFilter, TourServlet)

Tech Stack

Java 21+, Spring Boot, Spring Data JPA, PostgreSQL, Hibernate, JDBC, Gradle, Java Swing

Database Setup

Configure your PostgreSQL credentials depending on what you run:

- For Spring with JPA profiles:
  spring/src/main/resources/application-jpa.properties
  spring/src/main/resources/application-dev.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tourdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

- For raw JDBC backend / desktop / web:
  backend/src/main/resources/config.json

How to Run

Build everything:
.\gradlew build

Start Spring Boot REST API:
.\gradlew :spring:bootRun

Start with JPA profile active:
.\gradlew :spring:bootRun --args='--spring.profiles.active=jpa'

Start Swing UI:
.\gradlew :desktop:run

Start Web module (WAR build):
.\gradlew :web:war
(Deploy the generated WAR to a local Apache Tomcat instance)

API Endpoints

Spring Module (REST API):
GET /tours - Get all tours (optional query param: /tours?name=...)
POST /tours - Create a new tour
GET /tours/{id} - Get tour by ID
PUT /tours/{id} - Update tour
DELETE /tours/{id} - Delete tour
GET /tours/{tourId}/reviews - Get all reviews for a tour (requires JPA profile)
POST /tours/{tourId}/reviews - Add a review to a tour (requires JPA profile)
DELETE /tours/{tourId}/reviews/{reviewId} - Delete a review from a tour (requires JPA profile)

Web Module (Classic Servlets):
GET /tours - Get all tours
GET /tours?id={id} - Get tour by ID
POST /tours - Create a new tour
PUT /tours?id={id} - Update tour
DELETE /tours?id={id} - Delete tour
