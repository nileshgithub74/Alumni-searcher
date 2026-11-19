# 🎓 Alumni Searcher

A Spring Boot REST API application that helps search, collect, and manage university alumni profiles using PhantomBuster and LinkedIn search.

The application retrieves alumni profile information based on university and current designation, processes the results, and stores them in a PostgreSQL database.

## 🚀 Features

- 🔍 Search alumni profiles using university and designation.
- 🎓 Retrieve alumni information from LinkedIn search results through PhantomBuster.
- 💾 Store alumni profiles in PostgreSQL (NeonDB).
- 📋 Retrieve all stored alumni records.
- 🗑️ Delete individual alumni records.
- 🧹 Delete all alumni records.
- 🔗 Prevent duplicate profiles using LinkedIn profile URLs.
- ⚠️ Global exception handling and API validation.
- 📖 API documentation using Swagger UI.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Spring WebFlux WebClient | External API integration |
| PostgreSQL | Relational database |
| NeonDB | Cloud PostgreSQL hosting |
| PhantomBuster | LinkedIn search automation |
| Maven | Dependency management |
| Swagger / OpenAPI | API documentation |
| JUnit & Mockito | Unit testing |

## 🏗️ Project Architecture

```text
Client (Postman / Swagger)
          |
          v
    REST Controller
          |
          v
     Service Layer
          |
          v
    PhantomBuster API
          |
          v
    LinkedIn Search
          |
          v
    Process Results
          |
          v
      Mapper
          |
          v
    Repository (JPA)
          |
          v
    PostgreSQL (NeonDB)
```

## 📂 Project Structure

```text
src/main/java/com/alumni_searcher/alumni/
│
├── controller/
│   └── AlumniController.java
│
├── service/
│   ├── AlumniService.java
│   └── AlumniServiceImpl.java
│
├── repository/
│   └── AlumniRepository.java
│
├── entity/
│   └── Alumni.java
│
├── dto/
│   ├── RequestDTO.java
│   └── ResponseDTO.java
│
├── mapper/
│   └── Mapper.java
│
├── client/
│   └── PhantomBusterClient.java
│
└── common/
    ├── apiResponse/
    │   └── ApiResponse.java
    │
    └── exception/
        ├── GlobalExpection.java
        ├── PhantomBusterException.java
        └── PhantomBusterTimeoutException.java
```

## 🔌 API Endpoints

Base URL:

```text
/api/alumni
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/alumni/search` | Search and save alumni profiles |
| GET | `/api/alumni/all` | Retrieve all alumni records |
| DELETE | `/api/alumni/{id}` | Delete an alumni by ID |
| DELETE | `/api/alumni/delete-all` | Delete all alumni records |

### 1. Search Alumni

**POST** `/api/alumni/search`

Request body:

```json
{
  "university": "",
  "designation": "",
  "passoutYear": 
}
```

### 2. Get All Alumni

**GET** `/api/alumni/all`

Retrieves all alumni records stored in the database.

### 3. Delete Alumni

**DELETE** `/api/alumni/9`

Deletes an alumni record using its ID.

### 4. Delete All Alumni

**DELETE** `/api/alumni/delete-all`

Deletes all stored alumni records. This endpoint should be restricted to administrators.

## 📖 Swagger API Documentation

Swagger UI provides interactive API documentation.

Local URL:

```text
http://localhost:8080/swagger-ui/index.html
```

## ⚙️ Prerequisites

Before running the application, make sure you have:

- Java 21 (or the Java version configured in the project).
- Maven.
- PostgreSQL database or NeonDB account.
- PhantomBuster account and API key.
- Git.

## 🔧 Configuration

Configure the following environment variables before running the application:

| Variable | Description |
|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `PHANTOMBUSTER_API_KEY` | PhantomBuster API key |

Do not commit API keys, database passwords, or other secrets to GitHub.

## ▶️ Run Locally

Clone the repository:

```bash
git clone https://github.com/nileshgithub74/Alumni-searcher.git
```

Navigate into the project:

```bash
cd Alumni-searcher
```

Build the application:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## 🧪 Testing

The application uses:

- JUnit 5 for unit testing.
- Mockito for mocking dependencies.
- Postman and Swagger UI for API testing.

Run tests:

```bash
mvn test
```

## 🔐 Security Note

- Keep API keys and database credentials outside the source code.
- Use environment variables for production configuration.
- Protect destructive endpoints with appropriate authorization.
- Follow LinkedIn and PhantomBuster terms and applicable data-protection requirements.

## 🚀 Deployment

The application is intended to be deployed using Render, with NeonDB for cloud PostgreSQL hosting.

Deployment instructions will be added as the deployment process is completed.

## 👨‍💻 Author

**Nilesh Kumar**

GitHub: [@nileshgithub74](https://github.com/nileshgithub74)

---

⭐ If you find this project useful, consider giving it a star!
