# Profile Creation Application

A Spring Boot REST API for creating and managing user profiles with full CRUD operations.

## Overview

This project provides a RESTful API for managing user profiles. It supports creating profiles, retrieving all profiles, updating profiles (partial/PATCH), and deleting profiles.

## Technologies

- **Framework:** Spring Boot 3.5.0
- **Java Version:** 21
- **Database:** H2 (In-memory)
- **Build Tool:** Maven

## Dependencies

- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-data-jpa` - Database access
- `spring-boot-starter-validation` - Input validation
- `h2` - H2 database

## Project Structure

```
profile_creation/
└── practice/
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── example/
            │           └── practice/
            │               ├── PracticeApplication.java
            │               ├── controller/
            │               │   └── ProfileController.java
            │               ├── dto/
            │               │   ├── ProfileCreateRequest.java
            │               │   ├── ProfileResponse.java
            │               │   └── ProfileUpdateRequest.java
            │               ├── model/
            │               │   └── Profile.java
            │               ├── repository/
            │               │   ├── ProfileRepository.java
            │               │   └── ProfileRepositoryOld.java
            │               └── service/
            │                   └── ProfileService.java
            └── resources/
                └── application.properties
```

## Configuration

Located in `practice/src/main/resources/application.properties`:

```properties
spring.application.name=practice
```

Uses default H2 in-memory database configuration.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/profiles` | Create a new profile |
| GET | `/profiles` | Get all profiles |
| DELETE | `/profiles/{id}` | Delete a profile |
| PATCH | `/profiles/{id}` | Update profile (partial) |

## Data Models

### ProfileCreateRequest
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30,
    "bio": "Software developer"
}
```

### ProfileUpdateRequest
```json
{
    "name": "John Updated",
    "email": "john.updated@example.com"
}
```

### ProfileResponse
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30,
    "bio": "Software developer"
}
```

## Postman Request Examples

### 1. Create a New Profile

**Request:**
```
POST http://localhost:8080/profiles
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30,
    "bio": "Software developer"
}
```

**Response:**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30,
    "bio": "Software developer"
}
```

### 2. Get All Profiles

**Request:**
```
GET http://localhost:8080/profiles
```

**Response:**
```json
[
    {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com",
        "age": 30,
        "bio": "Software developer"
    }
]
```

### 3. Update Profile (Partial)

**Request:**
```
PATCH http://localhost:8080/profiles/1
Content-Type: application/json

{
    "name": "John Updated",
    "email": "john.updated@example.com"
}
```

**Response:**
```json
{
    "id": 1,
    "name": "John Updated",
    "email": "john.updated@example.com",
    "age": 30,
    "bio": "Software developer"
}
```

### 4. Delete Profile

**Request:**
```
DELETE http://localhost:8080/profiles/1
```

**Response:** No content (204)

## Running the Application

```bash
# Using Maven wrapper
cd profile_creation/practice
./mvnw spring-boot:run

# Or using Maven
cd profile_creation/practice
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Features

1. **Create Profile** - Add new user profiles with validation
2. **Get All Profiles** - Retrieve all profiles
3. **Partial Update (PATCH)** - Update specific fields without affecting others
4. **Delete Profile** - Remove profiles by ID
5. **Input Validation** - Jakarta Validation for request validation

## Code Highlights

### Profile Entity
```java
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String bio;
    // getters, setters, constructors
}
```

### Profile Controller
```java
@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @PostMapping
    public ProfileResponse create(@Valid @RequestBody ProfileCreateRequest request) { ... }
    
    @GetMapping
    public List<ProfileResponse> getAll() { ... }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { ... }
    
    @PatchMapping("/{id}")
    public ProfileResponse patch(@PathVariable Long id, @RequestBody ProfileUpdateRequest request) { ... }
}
```

