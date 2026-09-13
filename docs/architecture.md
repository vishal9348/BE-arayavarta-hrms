# Āryāvarta HRMS — Modular Monolith Architecture

## Runtime
One Spring Boot application (`hrms-application`) exposes the HTTP API on port 8080 and loads all domain modules in one JVM.

## Domain modules
identity, tenant, core-hr, workforce, leave, workflow, recruitment, onboarding, payroll, expense, performance, talent, experience, document, notification, integration, analytics, billing.

## Shared infrastructure
- persistence: JPA, PostgreSQL, Flyway
- security: Spring Security / resource-server foundation
- messaging: Spring Kafka foundation for domain/integration events
- observability: Actuator/metrics foundation

## Rules
1. A domain module owns its business rules and persistence model.
2. Modules communicate through application ports/services or domain events, not direct repository access into another module.
3. Avoid cross-module JPA entity relationships; reference other aggregates by ID.
4. Keep tenant context explicit on tenant-owned data.
5. Use local transactions; introduce outbox/event delivery for asynchronous integration.
6. Keep financial/audit evidence immutable.

## Deliberately excluded for this phase
Eureka Server, Config Server, API Gateway, and independent deployable service boundaries.
