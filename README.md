# QuoteApp

Simple quote management web application built with **Java Spring Boot** and **PostgreSQL**, deployed on **Render**.

Live demo: [https://quoteapp-l8us.onrender.com](https://quoteapp-l8us.onrender.com)

> A small pet project to experiment with Spring Boot, JPA, PostgreSQL and deployment to the cloud.

---

## Features

* Store quotes in a PostgreSQL database
* View all quotes
* Add new quotes
* (Optionally) get a random quote
* REST API built with Spring MVC
* Deployed as a web service on Render

---

## Tech Stack

* **Backend:** Java, Spring Boot
* **Database:** PostgreSQL (hosted on Render)
* **Build Tool:** Maven / Maven Wrapper
* **ORM:** Spring Data JPA / Hibernate
* **Deployment:** Render Web Service

---

## Project Structure

```text
src/
  main/
    java/
      com.example.quoteapp/
        QuoteappApplication.java
        controller/
        service/
        repository/
        model/
    resources/
      application.properties
      templates/      (if using Thymeleaf)
      static/         (if using static HTML/CSS/JS)
  test/
    java/
      com.example.quoteapp/
        ...           (tests)
```

---

## Getting Started (Local)

### Prerequisites

* JDK 17+ (or the version used in the project)
* Maven (or Maven Wrapper `./mvnw`)
* PostgreSQL running locally (or via Docker)

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/quoteapp.git
cd quoteapp
```

### 2. Create a PostgreSQL database

Example (Linux/macOS):

```bash
createdb quoteapp
```

Or via `psql`:

```sql
CREATE DATABASE quoteapp;
```

### 3. Configure environment variables

The application expects database configuration from environment variables:

* `SPRING_DATASOURCE_URL`
* `SPRING_DATASOURCE_USERNAME`
* `SPRING_DATASOURCE_PASSWORD`

For local development you can either:

#### Option A: Export env vars

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/quoteapp
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=your_password
```

#### Option B: Use `application.properties` (local only)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quoteapp
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Use PORT env var if present (Render compatible)
server.port=${PORT:8080}

# Optional: disable Open Session in View
# spring.jpa.open-in-view=false
```

### 4. Run the application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or with Maven:

```bash
mvn spring-boot:run
```

Or build and run the jar:

```bash
mvn clean package -DskipTests
java -jar target/quoteapp-0.0.1-SNAPSHOT.jar
```

The application should be available at:

```text
http://localhost:8080
```

---

## API (Example)

> Adjust this section to match your actual controller mappings.

### Get all quotes

```http
GET /api/quotes
```

**Response (example)**

```json
[
  {
    "id": 1,
    "author": "Goethe",
    "text": "Whatever you can do or dream you can, begin it."
  }
]
```

### Create a new quote

```http
POST /api/quotes
Content-Type: application/json

{
  "author": "Goethe",
  "text": "Knowing is not enough; we must apply."
}
```

### Get quote by id

```http
GET /api/quotes/{id}
```

### Get random quote (if implemented)

```http
GET /api/quotes/random
```

---

## Deployment on Render

The app is deployed as a **Web Service** on [Render](https://render.com).

### Environment variables (Render)

In the Render dashboard for the web service, configure:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST>:5432/<DBNAME>
SPRING_DATASOURCE_USERNAME=<USER>
SPRING_DATASOURCE_PASSWORD=<PASSWORD>
SPRING_PROFILES_ACTIVE=prod
JAVA_OPTS=-Xmx512m
```

These values are taken from the **PostgreSQL** instance created on Render.

### Build & Start commands (example)

**Build command:**

```bash
./mvnw clean package -DskipTests
```

or

```bash
mvn clean package -DskipTests
```

**Start command:**

```bash
java $JAVA_OPTS -jar target/quoteapp-0.0.1-SNAPSHOT.jar
```

Make sure `server.port` can read the `PORT` environment variable:

```properties
server.port=${PORT:8080}
```

---

## Notes

* `spring.jpa.open-in-view` is enabled by default in Spring Boot.
  For stricter separation of layers you may disable it:

  ```properties
  spring.jpa.open-in-view=false
  ```

* For production, avoid hardcoding any credentials in `application.properties`.
  Use environment variables instead.

---

## Future Improvements

* Authentication and user accounts
* Better validation and error handling
* Frontend UI for managing quotes
* Unit and integration tests
* CI/CD with GitHub Actions or similar

---

## License

This is a personal pet project.
You are free to use it as a reference or template for your own Spring Boot + PostgreSQL + Render deployments.
