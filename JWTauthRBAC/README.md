# JWT Authentication with Role-Based Access Control (RBAC)

A Spring Boot project implementing JWT (JSON Web Token) authentication with role-based authorization.

## Overview

This project demonstrates secure authentication using JWT tokens with Role-Based Access Control (RBAC). Users can register, login, and access different endpoints based on their assigned roles (ADMIN, USER, etc.).

## Technologies

- **Framework:** Spring Boot 3.5.0
- **Java Version:** 21
- **Database:** H2 (In-memory)
- **Security:** Spring Security + JWT
- **Build Tool:** Maven

## Dependencies

- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-security` - Security framework
- `spring-boot-starter-data-jpa` - Database access
- `spring-boot-starter-validation` - Input validation
- `h2` - In-memory database
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` - JWT handling
- `spring-boot-devtools` - Development tools

## Project Structure

```
JWTauthRBAC/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── practice/
        │               ├── PracticeApplication.java
        │               ├── controller/
        │               │   ├── AuthController.java
        │               │   └── UserController.java
        │               ├── dto/
        │               │   ├── AuthenticationRequest.java
        │               │   ├── AuthenticationResponse.java
        │               │   └── UserCreateRequest.java
        │               ├── entity/
        │               │   ├── Role.java
        │               │   └── User.java
        │               ├── exception/
        │               │   └── GlobalExceptionHandler.java
        │               ├── repository/
        │               │   ├── RoleRepository.java
        │               │   └── UserRepository.java
        │               ├── security/
        │               │   ├── CustomUserDetailsService.java
        │               │   ├── JwtAuthenticationFilter.java
        │               │   ├── JwtService.java
        │               │   ├── PasswordEncoderConfig.java
        │               │   └── SecurityConfig.java
        │               └── service/
        │                   └── UserService.java
        └── resources/
            └── application.properties
```

## Configuration

Located in `src/main/resources/application.properties`:

```properties
spring.application.name=practice
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT Configuration
application.security.jwt.secret-key=<your-secret-key>
application.security.jwt.expiration=86400000
application.security.jwt.refresh-token.expiration=604800000
```

## API Endpoints

### Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/auth/login` | Login and get JWT token | Public |
| POST | `/api/public/register` | Register a new user | Public |

### User Management

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/admin/dashboard` | Admin dashboard | ADMIN |

## Postman Request Examples

### 1. Register a New User

**Request:**
```
POST http://localhost:8080/api/public/register
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123"
}
```

**Response:**
```
User register Successfully!
```

### 2. Login (Get JWT Token)

**Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123"
}
```

**Response:**
```
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Access Protected Endpoint

**Request:**
```
GET http://localhost:8080/api/admin/dashboard
Authorization: Bearer <your-jwt-token>
```

**Response:**
```
Welcome, Admin. This is the dashboard
```

## Running the Application

```bash
# Using Maven wrapper
cd JWTauthRBAC
./mvnw spring-boot:run

# Or using Maven
cd JWTauthRBAC
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## H2 Console

Access the database console at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (empty)

## Security Features

1. **JWT Authentication** - Stateless authentication using JSON Web Tokens
2. **Password Encoding** - Passwords are encoded using BCrypt
3. **Role-Based Access Control** - Different endpoints accessible based on user roles
4. **Token Expiration** - JWT tokens expire after 24 hours
5. **Refresh Tokens** - Support for refresh tokens (7 days expiration)

## Entity Details

### User Entity
- `id` - Long (Primary Key)
- `username` - String (unique, required)
- `password` - String (encoded)
- `roles` - Set<Role> (many-to-many)

### Role Entity
- `id` - Long (Primary Key)
- `name` - String (ROLE_ADMIN, ROLE_USER, etc.)

## How It Works

1. **Registration:** User sends username/password to `/api/public/register`
2. **Login:** User sends credentials to `/api/auth/login`, receives JWT token
3. **Authentication:** Client includes JWT token in Authorization header
4. **Authorization:** Spring Security validates token and checks user roles
5. **Access:** User can access endpoints based on their assigned roles

