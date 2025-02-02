# Doctracer

**Doctracer** is a Spring Boot application written in Kotlin that provides a RESTful API for managing and analyzing document measurements, materials, and spectral data. This application was made in the scope of an engineering thesis. It includes:

- User authentication with JWT tokens (including roles and permissions).
- Database entities for devices, covered materials, covering materials, measurements, and samples.
- A seeder to populate mock data (only on the `dev` profile) for quick testing.
- REST endpoints for creating, reading, updating, and deleting various domain objects.

---

## Table of Contents

- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation and Running](#installation-and-running)
    - [Prerequisites](#prerequisites)
    - [Building and Running](#building-and-running)
    - [Profiles](#profiles)
- [Database Seeding](#database-seeding)
- [Authentication and Security](#authentication-and-security)
- [Endpoints Overview](#endpoints-overview)
    - [Auth Endpoints](#auth-endpoints)
    - [Admin Endpoints](#admin-endpoints)
    - [Materials Endpoints](#materials-endpoints)
    - [Devices Endpoints](#devices-endpoints)
    - [Measurements Endpoints](#measurements-endpoints)
    - [Samples Endpoints](#samples-endpoints)

---

## Key Features

1. **User Authentication & Authorization**:
    - JWT-based login and role-based method security.
    - Roles: `ADMIN`, `EDITOR`, `VIEWER`.
2. **Measurement & Spectral Data Management**:
    - Entities for different types of materials (`CoveredMaterial`, `CoveringMaterial`), `Measurement`, `Sample`, etc.
    - Automatic JSONB storage for spectral data in PostgreSQL.
3. **RESTful Endpoints**:
    - Comprehensive set of controllers to manage users, roles, devices, measurements, and samples.

---

## Technology Stack

- **Kotlin** (JDK 17+ recommended).
- **Spring Boot** (REST API, Security).
- **Spring Data JPA** (Persistence with repositories).
- **Hibernate** (ORM).
- **JWT** (JSON Web Token) for authentication/authorization.
- **PostgreSQL** (Recommended database, due to JSONB usage).

> Note: If you use a different database, ensure you adjust any Postgres-specific features (like JSONB columns).

---

## Installation and Running

### Prerequisites

1. **Java 17+** (Spring Boot requires Java 17 or later in newer versions).
2. **Maven or Gradle** (depending on your project setup – typically you will have either `pom.xml` for Maven or `build.gradle.kts` for Gradle).
3. A **PostgreSQL** database instance (or any database supported by Spring Data JPA).
    - If using PostgreSQL, ensure the `jsonb` column types are supported and configured.

### Building and Running

1. **Configure the application**:
    - Set up your application properties (in `application.properties`) for your database connection and JWT secret.

2. **Run the application**:
    - **Using Maven**:
      ```bash
      mvn spring-boot:run
      ```
    - **Using Gradle**:
      ```bash
      ./gradlew bootRun
      ```
3. Check the logs to confirm the application started successfully on the default port `8080`.
4. Test the API by accessing, for instance, `http://localhost:8080/auth/login`.

### Profiles

- **`dev`**: Enables the `DatabaseSeeder` to insert test data (admin user, sample measurements, etc.).
- **`prod`**: Production-like environment (no automatic seeding).

To activate a profile, use the `--spring.profiles.active=dev` (or `=prod`) flag:
```bash
java -jar build/libs/doctracer.jar --spring.profiles.active=dev
```

---

## Database Seeding

When you run the application with the `dev` profile, the **`DatabaseSeeder`** class inserts:

- **Default Roles**: `ADMIN`, `EDITOR`, `VIEWER`
- **Default Users**:
    - Username: `admin` / Password: `password1`
    - Username: `user1` / Password: `password1`
    - Username: `user2` / Password: `password2`
- **Sample devices, covered/covering materials, measurements, and sample spectral data**.

This data is intended to help you quickly test and explore the system.

---

## Authentication and Security

- The application uses **JWT-based security**.
- Endpoints require appropriate roles. For example:
    - `@PreAuthorize("hasRole('ADMIN')")` on Admin endpoints.
    - `@PreAuthorize("hasRole('EDITOR')")` on create/update endpoints.
    - `@PreAuthorize("hasRole('VIEWER')")` to read endpoints.
- Obtain a JWT by calling the `/auth/login` endpoint with valid credentials. Then include `Authorization: Bearer <token>` in subsequent requests.

---

## Endpoints Overview

> **Note**: All secured endpoints require an HTTP header `Authorization: Bearer <jwt>` after logging in.

### Auth Endpoints

| Method | Endpoint        | Description                      | Public/Secured |
|--------|-----------------|----------------------------------|----------------|
| POST   | `/auth/register`| Register a new user             | Public         |
| POST   | `/auth/login`   | Login with username and password | Public         |
| PUT    | `/auth/change-password` | Change the password for the currently logged-in user | Secured (any role) |

### Admin Endpoints

| Method | Endpoint                       | Description                      | Required Role |
|--------|--------------------------------|----------------------------------|--------------|
| GET    | `/admin/users`                | Get all users                    | ADMIN        |
| GET    | `/admin/users/{id}`           | Get user by ID                   | ADMIN        |
| PUT    | `/admin/users/{id}/activate`  | Activate a user                  | ADMIN        |
| PUT    | `/admin/users/{id}/deactivate`| Deactivate a user                | ADMIN        |
| PUT    | `/admin/users/{userId}/roles/{roleName}` | Assign a role to a user      | ADMIN        |
| DELETE | `/admin/users/{userId}/roles/{roleName}` | Remove a role from a user    | ADMIN        |
| PUT    | `/admin/users/bulk-update`    | Bulk update users                | ADMIN        |

### Materials Endpoints

**Covered Materials** (`/covered-materials`):
| Method | Endpoint                       | Description                      | Required Role           |
|--------|--------------------------------|----------------------------------|-------------------------|
| GET    | `/covered-materials`           | List all covered materials       | VIEWER                  |
| GET    | `/covered-materials/{id}`      | Get covered material by ID       | VIEWER                  |
| POST   | `/covered-materials`           | Create covered material          | EDITOR                  |
| PUT    | `/covered-materials/{id}`      | Update covered material          | EDITOR                  |
| DELETE | `/covered-materials/{id}`      | Delete covered material          | EDITOR                  |

**Covering Materials** (`/covering-materials`):
| Method | Endpoint                        | Description                      | Required Role           |
|--------|---------------------------------|----------------------------------|-------------------------|
| GET    | `/covering-materials`           | List all covering materials      | VIEWER                  |
| GET    | `/covering-materials/{id}`      | Get covering material by ID      | VIEWER                  |
| POST   | `/covering-materials`           | Create covering material         | EDITOR                  |
| PUT    | `/covering-materials/{id}`      | Update covering material         | EDITOR                  |
| DELETE | `/covering-materials/{id}`      | Delete covering material         | EDITOR                  |

### Devices Endpoints

| Method | Endpoint        | Description                          | Required Role |
|--------|-----------------|--------------------------------------|--------------|
| GET    | `/devices`      | List all devices                     | VIEWER       |
| GET    | `/devices/{id}` | Get device details by ID             | VIEWER       |

### Measurements Endpoints

| Method | Endpoint                      | Description                                          | Required Role |
|--------|------------------------------|------------------------------------------------------|--------------|
| GET    | `/measurements`              | List all measurements                                | VIEWER       |
| GET    | `/measurements/{id}`         | Get measurement by ID                                | VIEWER       |
| GET    | `/measurements/{id}/samples` | List all samples for a specific measurement          | VIEWER       |
| POST   | `/measurements`              | Create a new measurement                             | EDITOR       |
| PUT    | `/measurements/{id}`         | Update an existing measurement                       | EDITOR       |
| DELETE | `/measurements/{id}`         | Delete a measurement (and associated conditions)     | EDITOR       |

### Samples Endpoints

| Method | Endpoint         | Description                            | Required Role |
|--------|------------------|----------------------------------------|--------------|
| GET    | `/samples`       | List all samples (with limited data)   | VIEWER       |
| GET    | `/samples/{id}`  | Get a sample by ID (detailed)          | VIEWER       |
| POST   | `/samples`       | Create a new sample                    | EDITOR       |
| PUT    | `/samples/{id}`  | Update a sample                        | EDITOR       |
| DELETE | `/samples/{id}`  | Delete a sample                        | EDITOR       |
