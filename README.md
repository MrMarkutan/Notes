# Notes Spring Boot Project

A simple Java Spring Boot project for managing notes using Spring Data JPA and Thymeleaf.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)

## Introduction

The "Notes" Spring Boot project is a feature-rich application for managing notes, offering a seamless user experience with the power of the following technologies:

- **Java:** The core programming language driving the application's backend logic.
- **Spring Boot:** Providing a framework for building robust and scalable applications.
- **Spring Data JPA:** Simplifying data access and persistence with Java objects.
- **Thymeleaf:** A modern server-side Java template engine for dynamic web pages.
- **Bootstrap:** Enhancing the application's frontend with responsive and visually appealing design.
- **MySQL:** Used during development for database management, easily adaptable to other databases.
- **Maven:** Streamlining the build process and project management.
- **Docker:** Enabling containerization for consistent deployment across various environments.

Explore the rich features of this project, backed by a modern technology stack, making it suitable for various use cases and adaptable to your specific requirements.

## Features

- Create, read, update, and delete (CRUD) operations for contacts and addresses of your notes.
- Convenience search by different fields.
- Data storage using Spring Data JPA with flexible database options.
- User-friendly frontend with Thymeleaf and Bootstrap.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Thymeleaf
- Bootstrap
- MySQL (for development, can be replaced with other databases)
- Maven
- Docker

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

Make sure you have the following installed on your machine:

- Java Development Kit (JDK)
- Maven (or Gradle)
- Docker
- Docker-compose

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/MrMarkutan/Notes.git
   cd Notes
2. Run Docker Compose:

    ```bash
    docker-compose up -d

Optionally, configure the database connection parameters in docker-compose.yaml and src/main/resources/application.yml if needed. 


3. Start the Spring Boot application:

    ```bash
    mvn spring-boot:run
This will launch the Spring Boot application, connecting to the MySQL database container.

The application should now be accessible at http://localhost:9090. 
You can interact with the Notes application and explore its features.

# Contact

The `Contact` class represents a contact in the application. It is used to store information about individuals, including their name, address, birthdate, contact details, and related notes.

## Usage

The `Contact` class is a fundamental part of the application's data model, representing individual contacts with various attributes. It is utilized for CRUD operations and maintaining relationships between contacts.

# Address

The `Address` class represents a physical address in the application. It is used to store information about the location of a contact, including the country, city, street, building, and apartment.

## Usage

The `Address` class is an integral part of the application's data model, representing physical addresses associated with contacts. It is utilized for CRUD operations and maintaining relationships between contacts and their addresses.

# SearchResult

The `SearchResult` class is a simple data structure used to represent the result of a search operation in the application. It contains lists of contacts and addresses matching the search criteria.

## Usage

The `SearchResult` class is used to encapsulate the results of various search operations in the application. It provides a convenient way to organize and transport lists of contacts and addresses based on user search queries.
