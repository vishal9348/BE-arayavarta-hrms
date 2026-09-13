# Database
Single PostgreSQL database for the modular monolith. Tables remain owned by their domain modules. Use Flyway migrations; do not use Hibernate schema generation in production.
