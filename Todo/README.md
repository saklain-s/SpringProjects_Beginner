# Todo Application

A simple Spring Boot REST API for managing todo items with Create, Read, Update, and Delete operations.

## Overview 

This project provides a lightweight REST API for managing todo tasks. It allows users to add new todos, mark them as complete, retrieve all todos, and delete todos.

## Technologies

- **Framework:** Spring Boot 3.5.0
- **Java Version:** 21
- **Database:** H2 (In-memory)
- **Build Tool:** Maven

## Dependencies

- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-data-jpa` - Database access
- `h2` - H2 database
- `spring-boot-devtools` - Development tools

## Project Structure

```
Todo/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── practice/
        │               ├── PracticeApplication.java
        │               ├── config/
        │               │   └── AppConfig.java
        │               ├── controller/
        │               │   └── TodoController.java
        │               ├── model/
        │               │   └── Todo.java
        │               ├── repository/
        │               │   └── TodoRepository.java
        │               └── service/
        │                   └── TodoService.java
        └── resources/
            └── application.properties
```

## Configuration

Located in `src/main/resources/application.properties`:

```properties
spring.application.name=practice
```

The application uses default H2 in-memory database configuration.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| POST | `/api/todos` | Create a new todo |
| PUT | `/api/todos/{id}/complete` | Mark todo as complete |
| DELETE | `/api/todos/{id}` | Delete a todo |

## Data Model

### Todo
```json
{
    "id": "1",
    "title": "Buy groceries",
    "completed": false
}
```

## Postman Request Examples

### 1. Get All Todos

**Request:**
```
GET http://localhost:8080/api/todos
```

**Response:**
```json
[
    {
        "id": "1",
        "title": "Buy groceries",
        "completed": false
    }
]
```

### 2. Create a New Todo

**Request:**
```
POST http://localhost:8080/api/todos
Content-Type: application/json

{
    "title": "Buy groceries"
}
```

**Response:**
```json
{
    "id": "1",
    "title": "Buy groceries",
    "completed": false
}
```

### 3. Mark Todo as Complete

**Request:**
```
PUT http://localhost:8080/api/todos/1/complete
```

**Response:**
```json
{
    "id": "1",
    "title": "Buy groceries",
    "completed": true
}
```

### 4. Delete a Todo

**Request:**
```
DELETE http://localhost:8080/api/todos/1
```

**Response:**
```json
{
    "id": "1",
    "title": "Buy groceries",
    "completed": false
}
```

## Running the Application

```bash
# Using Maven wrapper
cd Todo/practice
./mvnw spring-boot:run

# Or using Maven
cd Todo/practice
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Features

1. **Get All Todos** - Retrieve list of all todo items
2. **Create Todo** - Add new todo with title
3. **Complete Todo** - Toggle todo completion status
4. **Delete Todo** - Remove a todo item
5. **In-Memory Storage** - Uses H2 database for data persistence

## Code Highlights

### Todo Entity
```java
@Entity
public class Todo {
    @Id
    private String id;
    private String title;
    private boolean completed;
    // getters, setters, constructors
}
```

### Todo Controller
```java
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @GetMapping
    public List<Todo> getTodos() { ... }
    
    @PostMapping
    public Todo add(@RequestBody Map<String, String> body) { ... }
    
    @PutMapping("/{id}/complete")
    public Todo updateTodo(@PathVariable String id) { ... }
    
    @DeleteMapping("/{id}")
    public Todo deleteTodo(@PathVariable String id) { ... }
}
```

