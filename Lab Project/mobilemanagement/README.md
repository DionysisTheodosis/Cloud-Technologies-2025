# 📱 Mobile Phones Management RESTful Service

## 📖 Overview

This project implements a **RESTful backend service** for managing mobile phones in an online store.  
It is built with **Spring Boot** and **Spring Data REST**, exposing repository-based REST endpoints with integrated validation and persistence via **JPA/Hibernate**.

The project includes:

1. A validated REST API for CRUD operations on mobile phones.  
2. Containerization with **Docker** and **Docker Compose**.  
3. Secure credentials management using **Google Secret Manager (GSM)**.  
4. Deployment to **Kubernetes** (via Minikube).  

---

## ✨ Features

### Mobile Phone Entity (all fields validated)

- **Serial Number** → exactly 11 alphanumeric characters, unique  
- **IMEI Number** → exactly 15 digits, unique  
- **Model** → alphanumeric, min 2 chars  
- **Brand** → letters only, min 2 chars  
- **Network Technology** → one or more of [GSM, HSPA, LTE, 3G, 4G, 5G]  
- **Number of Cameras** → integer (1–3)  
- **Number of CPU Cores** → integer ≥ 1  
- **Weight** → positive integer (grams)  
- **Battery Capacity** → positive integer (mAh)  
- **Cost** → positive decimal (€)  

### REST Endpoints

- **Create** → add a new phone (validates Serial Number & IMEI uniqueness)  
- **Read** → get phone by Serial Number, or list all  
- **Search** → query by any field, sorted by Brand → Model → Cost  
- **Update** → modify limited fields (cameras, cores, weight, battery, cost)  
- **Delete** → remove by Serial Number  

✅ Includes robust **error handling** with proper HTTP status codes and JSON responses.

---

## 🛠️ Technology Stack

- **Language:** Java 21+  
- **Framework:** Spring Boot 3.5.5, Spring Data REST, JPA/Hibernate Validator  
- **Database:** MySQL (Dockerized)  
- **Build Tool:** Maven  
- **Containerization:** Docker, Docker Compose  
- **Orchestration:** Kubernetes (Minikube)  
- **Secrets Management:** Google Secret Manager (GSM)  

---

## 🚀 Running Locally

### Prerequisites

- Java 21+  
- Maven  
- Docker & Docker Compose  
- Minikube (for Kubernetes)  
- GCP Service Account JSON (`./secrets/service_account.json`)  

### Environment

- Copy `.env.sample` → `.env` and update values.  
- `.env` is **ignored by Git** (never committed).  

---

### 🗄️ Run MySQL Only (for development)

```bash
   database/./setup-mysql.sh
```

- Starts MySQL container with persistent volume  
- Pulls credentials from GSM  
- Waits until database is ready  

---

### 🖥️ Run Full Backend (Dockerized)

1. **Build resources**

   ```bash
   docker/./setup.sh
   ```

2. **Start MySQL**

   ```bash
   database/./setup-mysql.sh
   ```

3. **Run backend**

   ```bash
   docker/./start.sh
   ```

   → Service available at: `http://localhost:8080`

4. **Stop services**

   ```bash
   docker/./stop.sh
   ```

5. **Remove containers**

   ```bash
   docker/./remove.sh
   ```

6. **Remove everything**

   ```bash
   FORCE_REMOVE=1 docker/./remove.sh
   ```

---

## 🐳 Docker Compose Setup

Start both backend + DB:

```bash
docker-compose up --build -d
```

- `--build` → rebuild if Dockerfiles changed  
- `-d` → run in background  

Logs:

```bash
docker-compose logs -f
```

Stop services:

```bash
docker-compose down
```

Remove everything:

```bash
docker-compose down -v --rmi all --remove-orphans
```

---

## ☸️ Kubernetes Setup (Minikube)

> **Prerequisites for this section:**
> Before applying the manifests, make sure you have:
>
> 1. **Minikube** and **kubectl** installed.
> 2. The `mysql-secret.yaml` file under `yaml/` containing your MySQL credentials.
>    Example `mysql-secret.yaml`:
>
>    ```yaml
>    apiVersion: v1
>    kind: Secret
>    metadata:
>      name: mysql-secret
>    type: kubernetes.io/basic-auth
>    stringData:
>      username: <YOUR_DB_USERNAME>
>      password: <YOUR_DB_PASSWORD>
>    ```
>
>    Replace `<YOUR_DB_USERNAME>` and `<YOUR_DB_PASSWORD>` with the credentials from your `.env` file.  

After this, you can proceed.

### 1. Start cluster

```bash
minikube start --nodes=2 --driver=docker
```

### 2. Label nodes

```bash
kubectl label node minikube componentType=APP
kubectl label node minikube-m02 componentType=DB
```

### 3. Apply resources

```bash
kubectl apply -f yaml/pv.yaml
kubectl apply -f yaml/mysql-claim.yaml
kubectl apply -f yaml/mysql-secret.yaml
kubectl apply -f yaml/mysql.yaml
kubectl apply -f yaml/mysql-service.yaml
```

### 4. Add Google SA secret

```bash
kubectl create secret generic google-sa-secret \
  --from-file=service_account.json=./secrets/service_account.json
```

### 5. Deploy backend

```bash
kubectl apply -f yaml/mobilemanagement.yaml
kubectl apply -f yaml/mobilemanagement-service.yaml
```

### 6. Expose service

```bash
minikube tunnel
```

Check service:

```bash
kubectl get svc mobilemanagement-service
```

→ Access via `127.0.0.1:8080`

---

## 🔐 Secrets Recap

- **MySQL credentials** → `mysql-secret.yaml`  
- **Google Cloud credentials** → `google-sa-secret`  

Both mounted inside containers at `/etc/secrets/`.

---

## ⚡ Developer Workflows

- **MySQL only (IDE testing):** `setup-mysql.sh`  
- **Full app:** `setup.sh` → `setup-mysql.sh` → `start.sh`  
- **Docker Compose:** `docker-compose up -d`  
- **Kubernetes:** `kubectl apply -f yaml/`  

---

## 📌 Notes

- Entity validation enforced with Jakarta Validator  
- Unique constraints at DB level  
- Multi-stage Docker build for optimized images  
- Health checks ensure DB readiness before backend starts  
- Non-root Docker/Kubernetes execution for better security  

---

## ✅ Summary

This project delivers a **secure, production-ready backend** for managing mobile phones, with:  

- Modular **Spring Boot + REST API**  
- Secure **secrets handling (GSM + K8s)**  
- Portable **Docker / Compose setup**  
- Scalable **Kubernetes deployment (Minikube)**  

---
