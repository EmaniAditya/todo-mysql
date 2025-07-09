# Todo MySQL API

A **Spring Boot 3** RESTful API for managing _Todo_ items backed by a **MySQL** database. It exposes CRUD endpoints, supports pagination, sorting & searching, and comes with **OpenAPI / Swagger UI** documentation out-of-the-box.

---

## Features

- Create, read, update & delete todo items
- Pagination & sorting for list/search endpoints
- Filter todos by status (PENDING, IN_PROGRESS, DONE)
- Full-text search on todo title
- Lombok-powered boilerplate reduction
- Automatic database schema generation via JPA/Hibernate
- Interactive Swagger UI at `/swagger-ui.html`

## Tech Stack

| Layer            | Technology |
|------------------|------------|
| Language         | Java 17    |
| Framework        | Spring Boot 3 |
| Persistence      | Spring Data JPA, Hibernate |
| Database         | MySQL 8.x |
| API Docs         | springdoc-openapi-starter |
| Build            | Maven      |

## Prerequisites

1. **Java 17** or newer
2. **Maven 3.9+**
3. **MySQL 8.x** running locally (or a remote instance)

> ✨ Quick tip: A local MySQL instance can be spun up via Docker:
>
> ```bash
> docker run --name mysql-todo -e MYSQL_ROOT_PASSWORD=yourPassword \
>            -e MYSQL_DATABASE=todo_db -p 3306:3306 -d mysql:8
> ```

## Database Setup

Create a database named `todo_db` (or change the name in `application.properties`).

```sql
CREATE DATABASE IF NOT EXISTS todo_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

By default the app connects with the MySQL **root** user. You can either:

1. Update `spring.datasource.username`/`password` in `src/main/resources/application.properties`, **or**
2. Override via environment variables when running the app:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/todo_db
export SPRING_DATASOURCE_USERNAME=myuser
export SPRING_DATASOURCE_PASSWORD=secret
```

---

## Running Locally

```bash
# Clone the repo
 git clone https://github.com/your-org/todo-mysql.git
 cd todo-mysql

# Build & run using Maven Wrapper
 ./mvnw spring-boot:run

# Alternative: Build the JAR & run
 ./mvnw clean package -DskipTests
 java -jar target/todo-mysql-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080`.

### Swagger / OpenAPI Docs

Open your browser at:

* JSON spec: `http://localhost:8080/api-docs`
* UI: `http://localhost:8080/swagger-ui.html`

---

## API Endpoints (v1)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/v1/todos` | List todos with pagination & optional `status`, `page`, `size`, `sortBy`, `direction` params |
| GET    | `/api/v1/todos/search?title=foo` | Search todos by partial title |
| GET    | `/api/v1/todos/{id}` | Fetch single todo |
| POST   | `/api/v1/todos` | Create new todo (JSON body) |
| PUT    | `/api/v1/todos/{id}` | Update existing todo |
| DELETE | `/api/v1/todos/{id}` | Delete todo |

Request / response schemas are available in the Swagger UI.

---

## Running Tests

```bash
./mvnw test
```

Unit tests use the in-memory **H2** database; they do **not** touch your MySQL instance.

---

## Deployment

The application is entirely self-contained. Any platform capable of running Java 17 and MySQL can host it. Popular options include:

- Docker / Kubernetes
- Heroku with JawsDB
- AWS ECS / RDS

---

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
