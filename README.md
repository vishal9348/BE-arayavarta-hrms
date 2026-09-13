# Āryāvarta HRMS

Enterprise HRMS built initially as a Maven multi-module modular monolith.

## Requirements
- Java 21
- Maven 3.9+
- PostgreSQL 17+

## Run PostgreSQL
`docker compose -f docker/docker-compose.yml up -d`

## Build
`mvn clean verify`

## Run
`mvn -pl hrms-application spring-boot:run`

Application: http://localhost:8080

This phase intentionally excludes Eureka Server, Config Server, and API Gateway.
