
# Mobile Phones Management RESTful Service

## Overview

This project implements a RESTful backend service for managing mobile phones in an online store. It is built with **Spring Boot** using **Spring Data REST** to expose repository-based REST endpoints and includes integration with a database via **JPA/Hibernate**.

The project covers the following main tasks:

1. Implementing the RESTful service with validation and CRUD operations.
2. Dockerizing the backend service and database.
3. Modeling the backend with Docker Compose.
4. Modeling the backend with Kubernetes manifests deployable via Minikube.
5. Writing a report describing the implementation details and experience.

---

## Project Features

- **Mobile Phone Entity** with the following validated fields (all mandatory):
  - Serial Number (exactly 11 alphanumeric characters, unique)
  - IMEI Number (exactly 15 digits, unique)
  - Model (alphanumeric, min 2 chars)
  - Brand (letters only, min 2 chars)
  - Network Technology (one or more from [GSM, HSPA, LTE, 3G, 4G, 5G])
  - Number of Cameras (integer between 1 and 3)
  - Number of CPU Cores (integer ≥ 1)
  - Weight (grams, positive integer)
  - Battery Capacity (mAh, positive integer)
  - Cost (€ positive decimal)

- **REST Endpoints** (via Spring Data REST & custom validation):
  - Create mobile phone (enforces uniqueness of Serial Number and IMEI)
  - Read mobile phone by Serial Number or all mobiles if no ID given
  - Search mobiles by any attribute with sorting by Brand, then Model, then Cost
  - Update mobiles (only fields allowed to change: number of cameras, cores, weight, battery, cost)
  - Delete mobile by Serial Number

- Proper error handling and validation responses.

---

## Technology Stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x, Spring Data REST, Spring Data JPA, Hibernate Validator
- **Database:** PostgreSQL (or configurable relational DB)
- **Build Tool:** Maven
- **Containerization:** Docker, Docker Compose
- **Orchestration:** Kubernetes (Minikube for local cluster)

---

## How to Run Locally

### Prerequisites

- Java 17+ installed
- Maven installed
- PostgreSQL installed and running (or use Dockerized DB)
- Docker and Docker Compose installed (for containerized deployment)
- Minikube installed (for Kubernetes deployment)

### Setup Database

- Create database and user as per `application.properties` or use Dockerized PostgreSQL container.
- Optionally run `schema.sql` script if not using JPA auto DDL.

### Running Backend

```bash
mvn clean spring-boot:run
```

The service will start on `http://localhost:8080`.

---

## Docker Setup

### Build Docker Image

Run the provided `setup.sh` (Linux/macOS) or `setup.bat` (Windows) to build the Docker image for the backend service.

### Start Containers

Run the provided `start.sh` / `start.bat` to start the backend and database containers:

- Creates a Docker network for secure communication.
- Starts PostgreSQL container with volume for persistence.
- Waits for DB readiness before starting the backend container.

### Stop Containers

Run the provided `stop.sh` / `stop.bat` to stop and remove containers, network, and optionally volumes.

---

## Docker Compose Setup

Use the included `docker-compose.yml` to start the backend and database with one command:

```bash
docker-compose up --build
```

The compose file defines:

- Backend service build and run instructions.
- PostgreSQL service with volume and network.
- Health checks, environment variables, and restart policies.

---

## Kubernetes Setup

All Kubernetes manifests are in the `/yaml` directory and include:

- Deployment for the backend REST service.
- StatefulSet for PostgreSQL with persistent volume claim.
- Services (ClusterIP and Headless).
- ConfigMaps and Secrets for environment variables and DB credentials.
- Resource limits, restart policies, and readiness probes.

To deploy on Minikube:

```bash
kubectl apply -f yaml/
```

To delete the deployment:

```bash
kubectl delete -f yaml/
```

---

## Configuration

- Database connection parameters (URL, username, password) are configurable via `application.properties` or environment variables.
- Docker and Kubernetes manifests use environment variables and secrets to inject these parameters securely.

---

## Report

The accompanying report includes:

- Details on technology choices (Java, Spring Boot, Spring Data REST, PostgreSQL).
- Use of Maven for build automation.
- Database schema and initialization instructions.
- How the backend service is built and executed.
- Configuration changes for Docker, Docker Compose, and Kubernetes deployments.
- Lessons learned and challenges encountered during the project.

---

## Notes

- Validation is enforced both at entity level (Hibernate Validator) and in service logic.
- The uniqueness of Serial Number and IMEI is guaranteed by unique constraints in the DB.
- Search endpoint supports filtering by any field with combined criteria.
- Error handling uses standard HTTP response codes and JSON error messages.
- Docker multi-stage build optimizes the backend image size.
- Scripts include health checks to ensure DB readiness before starting backend.
- Security considerations applied in Dockerfile and deployment manifests (non-root user, secrets).

---

*This README provides the complete guide for building, running, and deploying the Mobile Phones Management RESTful service using Spring Boot, Docker, Docker Compose, and Kubernetes.* 
