# MeFit App - Backend

## Table of contents
- [About](#about)
    - [Team Members](#team-members)
- [Techinal Description](#technical-description)
- [Getting Started](#getting-started)


## About

MeFitApp is a web application for managing weekly workout goals. This repository contains
the backend for it. 

Link to [Frontend repository](https://github.com/paularintaharri/MefitApp-frontend)  
Link to [API documentation](https://documenter.getpostman.com/view/14576152/TzCTZkVH)

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
  a normal user can only access goals created by themself (in other words where the goal's
  profile ID value matches the user's profile ID value). A user with an admin role can however
  access all the goals.
  
## Getting Started

