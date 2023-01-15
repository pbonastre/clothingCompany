# Challenge

## Overview

Technical Test Inc. is a clothing company that recently had a big commercial success. As a result, the
number of transactions has dramatically increased, and the old system doesn't scale to the required
transaction rate on peak hours.

For this reason the system has been redesigned and one of the key pieces is the new payment processor.
This new microservice will consume payments from the message broker and process them at its own pace.
In addition, it will communicate via REST API with third parties for validation and logging. Your goal is to
implement this microservice.

### Built With

### Tecnologías

- [Spring Boot](https://spring.io/projects/spring-boot) - To create standalone applications that run on their own.
- [maven](https://maven.apache.org/)   - To automate builds, testings and deployment.
- [MYSQL](https://www.mysql.com/) - MySQL is a widely used relational database management system (RDBMS). MySQL is free
  and open-source.
- [JUnit5](https://junit.org/junit5/) -
- [Lombok](https://projectlombok.org/) - Library to reduce Java code to auto-generate all the boilerplate code during
  compiling time.
- [JPA](https://spring.io/projects/spring-data-jpa) - Java specification for accessing, persisting and managing data
  between java objects/classes and the database.
- [Kafka](https://kafka.apache.org/) - Apache Kafka is an open-source distributed event streaming platform.
- [docker](https://www.docker.com/) - Docker is a set of platform as a service products that use OS-level virtualization
  to deliver software in packages called containers.

### How to run?

To execute the process

1.- docker-compose up . Use -d to start in detached mode and run the containers
in the background.

2.- http://localhost:9000/ and press, Start test

3.- mvn spring-boot:run
