NTU SC2002 AY24/25 Assignment - Build-To-Order (BTO) Management System

Done By:
Elkan Ong Han’en
Jindal Khanak
Kanteti Kavya Vishnu
Mallvin Rajamohan
Vemu Malishka

# About the Project
The BTO Management System is a Java CLI application that makes use of Object-Oriented concepts to manage BTO projects.
The program is aimed at being easy to scale and extend in the future with minimal impact to existing infrastructure.
The system can accommodate different user types with ease and provide them with the capabilities they require.

# Highlights
## Persistent Storage using CSV Files
Instead of a traditional database, the application uses CSV files to store and retrieve persistent data.
This allows data in the BTO system to remain even after exiting the program.
## Registration
Initially, adding new users required manual editing of CSV files, which was prone to user error and not scalable.
We developed a user registration feature that allows users to create a specific user type (e.g., `Applicant`, `Officer`, `Manager`) and input their information directly through the system.
## Inheritance and Extensibility through Abstract User Class
The system allows for easy extensibility through the User class
It defines the common attributes and behaviors shared across all user types, which helps us to make use of the Open/Closed Principle to add new user types with minimal changes to the existing system much if required

# Features
## Applicants
* View BTO projects open for application
* Apply for an open BTO project they are eligible for
* View application and its status
* Enquire about a BTO project
* Edit/Delete Enquiries made
* Withdraw from a BTO project they applied for
* View withdrawal and its status
* Book a flat after a successful application

## Officers
* Everything an `Applicant` can do
* Register for a BTO project they have no intention of applying for
* View their registration and its status
* View and reply to enquiries made to their assigned BTO project
* View their assigned BTO project's details
* Handle flat bookings of successful `Applicants`

## Managers
* View the BTO project they are managing
* Create a new BTO project
* Edit the BTO project they are managing
* Delete the BTO project they are managing
* View all BTO projects created
* View registrations made by `Officers` for the BTO project they are managing
* View Applicants` applications for the BTO project and approve/reject them
* View `Applicants` withdrawal requests and approve/reject them
* Generate a report of applicants who have booked units in the manager's BTO projects
* View and reply to enquiries made to their BTO projects
* Toggle the visibility of the BTO project they are managing

`Applicants`, `Officers`, and `Managers` are all able to create a filter to view projects they are interested in based on neighbourhood, flat type, and price.

## Run
Either:
Run the jar file with the following command when in the repository: <br>
```bash
java -jar ./out/artifacts/SC2002Proj_jar/SC2002Proj.jar
```
Or you can use JetBrains IntelliJ IDEA to run the project using the `BTOApp.java`

