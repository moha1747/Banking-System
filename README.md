# Banking System Project

## Introduction
This project is a banking system implemented in Java and deployed on Apache Tomcat 9. It provides basic banking functionalities such as creating accounts, transferring funds, and viewing transaction history.

## Installation
1. Download and install Apache Tomcat 9 from the [official website](https://tomcat.apache.org/download-90.cgi). # 64bit version
2. Clone this repository or download the project files.
3. Build the project using your preferred IDE or build tool. Ensure that the project generates a .war file.

## Deployment
1. Copy the generated .war file to the webapps directory in your Apache Tomcat installation directory.
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
- Java
- Apache Tomcat 9
- HTML/CSS (for the frontend)
