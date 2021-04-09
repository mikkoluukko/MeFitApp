# MeFit App - Backend

## Table of contents
- [About](#about)
    - [Team Members](#team-members)
- [Techinal Description](#technical-description)
- [Links and Documentation](#links-and-documentation)
- [Getting Started](#getting-started)


## About

MeFitApp is a web application for managing weekly workout goals. This repository contains
the backend for it.


### Team Members

[Mikko Luukko](https://github.com/mikkoluukko)  
[Paula Rinta-Harri](https://github.com/paularintaharri)  
[Mikko Siukola](https://github.com/MJS1977)


## Technical Description
- The backend is build with Spring Boot and it uses a PostgreSQL database
  mapped and modeled with Hibernate.
- It offers a RESTful API service with full CRUD functionality
  implemented using Spring Data JPA and protected with Spring Security.
- It uses OAuth2 authorization implemented with a separate Keycloak server.
- Resources are protected by both role based and user ID based access rights. For example,
  a normal user can only access goals created by themselves (in other words where the goal's
  profile ID value matches the user's profile ID value). A user with an admin role can however
  access all the goals.


## Links and Documentation

Database diagram:  
![database_diagram](https://github.com/mikkoluukko/MeFitApp/blob/master/readme-images/database_diagram.png)  

Link to [API documentation](https://documenter.getpostman.com/view/14576152/TzCTZkVH)  
Link to [Frontend repository](https://github.com/paularintaharri/MefitApp-frontend)  
Link to [live application deployed to Heroku](https://mefit-app.herokuapp.com/)  

## Getting Started
The application requires a separate Keycloak server. Instruction for setting up a Keycloak server
can be found [here](https://www.keycloak.org/). In the Keycloak server you will need to set up
a realm with the name "mefitapp" and add user roles "User", "Contributor" and "Admin".

After Keycloak server is available just clone, build and run this repository.
