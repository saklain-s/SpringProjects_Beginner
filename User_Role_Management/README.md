# User Role Management System

A Spring Boot application demonstrating user management with role-based access control (RBAC) using Spring Security and JWT authentication.

## Overview

This project implements a complete user authentication and authorization system with multiple user roles. It features user registration, JWT-based login, and role-based endpoint protection.

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

## Project Structure

```
User_Role_Management/
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
            ├── application.properties
            └── data.sql
```

## Configuration

Located in `src/main/resources/application.properties`:

```properties
spring.application.name=practice

# H2 Database
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
application.security.jwt.secret-key=404D635166546A576E5A7234753778214125442A462D4A614E645267556B58703273357638792F423F4528482B4D6251655368566D597133743677397A24432646294A404E635266556A586E5A7234753778214125442A462D4A614E645267556B58703273357638792F423F4528482B4D6251655368566D597133743677397A24432646294A404E635266556A586E5A72347537782141
application.security.jwt.expiration=86400000
application.security.jwt.refresh-token.expiration=604800000
```

## Database Initialization

The `data.sql` file initializes default roles:

```sql
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');
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
| GET | `/api/admin/dashboard` | Admin dashboard | ADMIN only |

## Postman Request Examples

### 1. Register a New User

**Request:**
```
POST http://localhost:8080/api/public/register
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
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
    "username": "admin",
    "password": "admin123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Access Admin Dashboard

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
cd User_Role_Management
./mvnw spring-boot:run

# Or using Maven
cd User_Role_Management
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## H2 Console

Access the database console at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (empty)

## Security Features

1. **JWT Authentication** - Stateless token-based authentication
2. **BCrypt Password Encoding** - Secure password storage
3. **Role-Based Authorization** - Access control based on user roles
4. **Token Expiration** - JWT tokens expire after 24 hours
5. **Refresh Tokens** - Support for refresh tokens (7 days)

## Entity Details

### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
```

### Role Entity
```java
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name; // ROLE_ADMIN, ROLE_USER
}
```

## How It Works

1. **User Registration:** User registers via `/api/public/register`
2. **Login:** User authenticates at `/api/auth/login` and receives JWT token
3. **Token Validation:** JWT filter validates token on each protected request
4. **Role Check:** Spring Security checks user roles before granting access
5. **Access Granted:** User can access endpoints matching their role permissions

