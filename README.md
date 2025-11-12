🖥️ Online Examination Portal

An Online Examination Portal application built with Spring Boot that manages the entire examination lifecycle: Teacher creation of questions, Admin scheduling of exams, Student examination access, and automatic result generation. This system provides comprehensive role-based features and a modern user interface.

✨ Features and Workflow

This application implements a precise examination workflow managed across three core user roles:

Teacher Functionality (Question Creation):

Teachers can log in and create, manage, and submit examination questions to the system.

They define the content, correct answers, and associated subjects for all questions.

Admin Functionality (Exam Scheduling):

Admins have the authority to review submitted questions and create/schedule exams at a specific date and time.

Admins manage user accounts and define which students are eligible for which exams.

Student Functionality (Exam Taking & Results):

Students can log in to the portal. Access to the exam is only granted during the scheduled time window.

Upon completion, the system immediately calculates and generates the examination result automatically.

Technical Features: Built on Spring Boot for robust backend logic and a clean, responsive front-end framework.

🚀 Getting Started

Follow these steps to set up and run the project on your local machine. You will be using Spring Tool Suite (STS) for development.

📋 Prerequisites

Ensure the following software is installed on your system:

Java Development Kit (JDK) 17+

Apache Maven (for dependency management and building)

MySQL Database (e.g., MySQL Workbench or a similar client)

Key Project Dependencies (Added via STS Initializer):
The project is built using the following core Spring Boot dependencies, which are typically included when creating the project in Spring Tool Suite (STS) or using the Spring Initializr:

Spring Data JPA: Used for interacting with the MySQL database via ORM (Object-Relational Mapping).

Spring Web (MVC): Enables the creation of the web application, handling requests, and defining RESTful endpoints.

MySQL Driver (Connector/J): The JDBC driver necessary to connect the Spring application to the MySQL database.

Spring Boot DevTools: Provides development-time features like automatic application restarts upon code changes.

⚙️ Installation and Setup

1. Database Setup

The first step is to create the necessary database and prepare the schema using MySQL.

Open your MySQL Workbench or command-line client.

Create the required database as specified for this project:

CREATE DATABASE online_examination;





2. Configuration Update

You need to update the database credentials in the Spring Boot configuration file.

Navigate to the configuration file:

src/main/resources/application.properties





Find the following lines related to MySQL connection and update them to match your local setup. Ensure the database name matches online_examination.

# Database Configuration Properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_examination
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE





3. Build and Run

With the database configured, you can now build and launch the application using Spring Tool Suite (STS).

Open Spring Tool Suite (STS):

Import the project as an existing Maven project.

Run the Application:

Right-click the project in the Project Explorer.

Select Run As -> Spring Boot App.

The application will now start, typically accessible at http://localhost:8080 unless configured otherwise.
