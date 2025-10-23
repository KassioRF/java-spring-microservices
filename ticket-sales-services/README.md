# Ticket Sales Services

[WIP] Ticket Sales Services – Microservice architecture for managing users, sales, and notifications.

---
## Microservices Overview

Groups all microservices related to ticket sales. See individual folders for more details:
  - [`users`](./tickets/users): User management microservice
  <!-- - [`sales`](./sales): Ticket sales microservice (coming soon) -->
  <!-- - [`notifications`](./notifications): Notification microservice (coming soon) -->

### Building & Running

Each microservice is a standalone Spring Boot application with its own `pom.xml`.
To build and run each microservice, navigate to its folder and use Maven commands:

```bash
cd tickets/users # Navigate to the desired microservice folder (e.g., users, sales, notifications)

# when running for the first time, add permission to the mvnw script
# chmod +x mvnw

# run the application
./mvnw spring-boot:run

```

---


## Database

The database layer is defined in [`docker/compose-db.yml`](docker/compose-db.yml).

- **DBMS**: PostgreSQL `postgres:17.6-alpine3.22`
- **Architecture**: Single instance managing multiple databases (one per microservice)

### Structure & Initialization

- Custom `.sh` scripts in [`docker/database`](docker/database) are used to:
  - Create individual users
  - Create dedicated databases
  - Assign ownerships accordingly
- Scripts are automatically executed on first container startup via Docker’s `docker-entrypoint-initdb.d` hook
- Data is persisted in the local volume: [`docker/volumes/pg_data`](docker/volumes/pg_data)

### Environment Setup

A valid `.env` file is required to initialize the service.  
Use [`docker/.env.example`](docker/.env.example) as a reference for the required variables (users, passwords, database names, etc.).

Start the service:

```bash
cd docker
docker compose --env-file ../.env.example -f compose-db.yml up
```

### Manual Checks (after first run)
After the initial run, you can verify the databases, users and ownerships from inside the container:
```bash
# Access the container shell
docker exec -it pg-dbms bash
# Connect to PostgreSQL
psql -U ${POSTGRES_USER}
```

Then run:
```sql
-- List all databases
\l

-- List all users/roles
\du

-- Check each database's owner
SELECT datname AS database, datdba::regrole AS owner
FROM pg_database
WHERE datistemplate = false
ORDER BY datname;

```
