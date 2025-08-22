# Mobile Phones Management RESTful Service

## Overview

This project implements a RESTful backend service for managing mobile phones in an online store. It is built with **Spring Boot** using **Spring Data REST** to expose repository-based REST endpoints and includes integration with a database via **JPA/Hibernate**.

The project covers the following main tasks:

1. Implementing the RESTful service with validation and CRUD operations.
2. Dockerizing the backend service and database.
3. Using Google Secret Manager to securely store database credentials.
4. Modeling the backend with Docker Compose.
5. Modeling the backend with Kubernetes manifests deployable via Minikube.
6. Writing a report describing the implementation details and experience.

---

## Project Features

* **Mobile Phone Entity** with the following validated fields (all mandatory):

  * Serial Number (exactly 11 alphanumeric characters, unique)
  * IMEI Number (exactly 15 digits, unique)
  * Model (alphanumeric, min 2 chars)
  * Brand (letters only, min 2 chars)
  * Network Technology (one or more from \[GSM, HSPA, LTE, 3G, 4G, 5G])
  * Number of Cameras (integer between 1 and 3)
  * Number of CPU Cores (integer ≥ 1)
  * Weight (grams, positive integer)
  * Battery Capacity (mAh, positive integer)
  * Cost (€ positive decimal)

* **REST Endpoints** (via Spring Data REST & custom validation):

  * Create mobile phone (enforces uniqueness of Serial Number and IMEI)
  * Read mobile phone by Serial Number or all mobiles if no ID given
  * Search mobiles by any attribute with sorting by Brand, then Model, then Cost
  * Update mobiles (only fields allowed to change: number of cameras, cores, weight, battery, cost)
  * Delete mobile by Serial Number

* Proper error handling and validation responses.

---

## Technology Stack

* **Language:** Java 21+
* **Framework:** Spring Boot 3.5.5, Spring Data REST, Spring Data JPA, Hibernate Validator
* **Database:** MySQL (Dockerized)
* **Build Tool:** Maven
* **Containerization:** Docker, Docker Compose
* **Orchestration:** Kubernetes (Minikube for local cluster)
* **Secrets Management:** Google Secret Manager

---

## How to Run Locally

### Prerequisites

* Java 21+ installed
* Maven installed
* Docker and Docker Compose installed
* Minikube installed (for Kubernetes deployment)
* GCP Service Account JSON for Secret Manager (placed under `./secrets/`)

### Environment File

* Create a `.env` file in the project root (copy from `.env.example`) with at least:

```bash
MYSQL_CONTAINER_NAME="sample"
MYSQL_VOLUME_NAME="sample"
MYSQL_IMAGE="mysql:latest"
NETWORK_NAME="sample"
APP_IMAGE_NAME="sample"
APP_CONTAINER_NAME="sample"
APP_PORT=8080
PROFILE="sample"
GCP_PROJECT_ID="your-gcp-project-id"
GOOGLE_APPLICATION_CREDENTIALS="./secrets/your-service-account.json"
```

> `.env` is **never committed** to GitHub. Only `.env.example` is shared as a template.

---

### Running MySQL Only (Development Mode)

Sometimes you only want **MySQL running** to test backend from an IDE or editor:

```bash
# Load environment variables
source scripts/env.sh

# Initialize MySQL container using Google Secret Manager
./database/setup-mysql.sh
```

* Creates MySQL container with a persistent volume
* Fetches DB credentials from GSM
* Initializes database and app user
* Waits for MySQL to be ready

> If the volume exists, it **reuses existing data**. To reinitialize MySQL from scratch:

```bash
FORCE_INIT=1 ./database/setup-mysql.sh
```

---

### Running the Full Backend Service

1. **Setup Docker resources**

```bash
./Docker/setup.sh
```

* Builds backend Docker image
* Creates MySQL volume and Docker network

2. **Start MySQL and wait for readiness**

```bash
./database/setup-mysql.sh
```

3. **Start backend container**

```bash
./Docker/start.sh
```

* Waits for MySQL readiness
* Injects environment variables and GSM credentials
* Runs Spring Boot app on `http://localhost:8080`

4. **Stop Containers**

```bash
./Docker/stop.sh
```

5. **Remove Containers / Volumes / Network (optional)**

```bash
FORCE_REMOVE=1 ./Docker/remove.sh
```

---

## Docker Compose Setup

Use the included `docker-compose.yml` to start the backend and database with one command:

```bash
docker-compose up --build
```

* Ensures volume and network are created
* MySQL waits for readiness before backend starts

---

## Kubernetes Setup

All Kubernetes manifests are in `/yaml`:

* Deployment for backend REST service
* StatefulSet for MySQL with PersistentVolumeClaim
* Services (ClusterIP / Headless)
* ConfigMaps / Secrets for DB credentials from GSM
* Resource limits, restart policies, and readiness probes

Deploy on Minikube:

```bash
kubectl apply -f yaml/
```

Delete deployment:

```bash
kubectl delete -f yaml/
```

---

## Configuration

* Database credentials are fetched **securely from Google Secret Manager**
* `.env` contains only **non-sensitive defaults** for Docker containers and networking
* Docker and Kubernetes manifests use environment variables and secrets to inject parameters securely

---

## Notes

* Validation enforced at entity level (Hibernate Validator) and service logic
* Uniqueness of Serial Number and IMEI guaranteed by database constraints
* Search endpoint supports filtering by any field with combined criteria
* Error handling uses standard HTTP response codes and JSON error messages
* Docker multi-stage build optimizes image size
* Scripts include health checks to ensure DB readiness before starting backend
* Security considerations applied in Dockerfile and deployment manifests (non-root user, secrets)

---

## Summary of Developer Workflows

1. **MySQL only**: run `setup-mysql.sh` → use DB for development in any IDE
2. **Full app**: run `setup.sh` → `setup-mysql.sh` → `start.sh` → backend ready
3. `.env` + GSM ensures **secure, repeatable, modular configuration**

---

*This README provides the complete guide for building, running, and deploying the Mobile Phones Management RESTful service using Spring Boot, Docker, Docker Compose, Kubernetes, and Google Secret Manager.*

---