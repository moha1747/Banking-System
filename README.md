# Banking System Project

## Introduction
This project is a banking system implemented in Java and deployed on Apache Tomcat 9. It provides basic banking functionalities such as creating accounts, transferring funds, and viewing transaction history.

## Installation
1. Download and install Apache Tomcat 9 from the [official website](https://tomcat.apache.org/download-90.cgi). (64bit version)
2. Download Lab-Banking.war file or clone the repository.
3. Here are resources to get started on [Mac](https://gist.github.com/emadpres/a17c310b9be8c41dc632b5b699af2e1c) and [Windows]()


## Deployment
1. Copy the .war file to the webapps directory in your Apache Tomcat installation directory.
2. Start Apache Tomcat by running the `startup.sh` (Linux/Mac) or `startup.bat` (Windows) script located in the `bin` directory of your Tomcat installation.

## Accessing the Application
Once Tomcat is running, you can access the application by navigating to `http://localhost:8080/<war_filename>` in your web browser, where `<war_filename>` is the name of your .war file without the .war extension.

## Usage
1. Open the application in your web browser.
2. Create a new account by providing the required information.
3. Log in using your account credentials.
4. Perform banking operations such as deposit, withdrawal, and fund transfer.
5. View your transaction history to track your financial activities.

## Technologies Used
- Java 17
- Apache Tomcat 9
- HTML/CSS
