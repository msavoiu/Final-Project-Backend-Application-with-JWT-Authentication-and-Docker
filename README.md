# Final Project: Backend Application with JWT Authentication and Docker

For this project, I decided to implement a backend system for a skate shop to handle orders with.

![](./readmeImages/erdiagram.png)

## Prerequisities
**Java version:** 17 <br>
**Docker version:** 29.4.2

### Database setup instructions
1. Log into the default PostgreSQL database using `psql -U <username> -d postgres`
2. Create a new database called `cpsc449final` with `CREATE DATABASE cpsc449final`
3. Start up the backend system for the first time with `mvn spring-boot:run` to initialize the database with the necessary tables.

## Docker build command
```bash
docker build -t demo-app:1.0 . 
```

## Docker run command
```bash
docker run -d --name demo-app -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/cpsc449final \
  -e SPRING_DATASOURCE_USERNAME=madeline \
  -e SPRING_DATASOURCE_PASSWORD= \
  demo-app:1.0
```

## Postman example requests

### POST /api/auth/register
![](./readmeImages/endpoint1.png)

### POST /api/brand
Unauthenticated:
![](/readmeImages/endpoint2_unauthenticated.png)
Authenticated:
![](/readmeImages/endpoint2_authenticated.png)