# Notes Application (Full-Stack)

A full-stack notes application with a Spring Boot backend and React frontend.

## Overview

This project is a complete notes management application consisting of:
- **Backend:** Spring Boot REST API
- **Frontend:** React application

Users can create, read, and delete notes through an intuitive web interface.

## Technologies

### Backend
- **Framework:** Spring Boot
- **Java Version:** 21
- **Database:** H2 (In-memory)
- **Build Tool:** Maven

### Frontend
- **Framework:** React
- **Package Manager:** npm
- **Port:** 3000

## Project Structure

```
notes_projects/
├── notes-client/          # React Frontend
│   ├── public/
│   │   ├── index.html
│   │   └── ...
│   ├── src/
│   │   ├── components/
│   │   │   └── NoteForm.js
│   │   ├── App.js
│   │   └── ...
│   ├── package.json
│   └── README.md
│
└── notes-server/          # Spring Boot Backend
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/
            │       └── example/
            │           └── practice/
            │               ├── PracticeApplication.java
            │               ├── controller/
            │               │   └── NoteController.java
            │               ├── model/
            │               │   └── Note.java
            │               ├── repository/
            │               │   └── NoteRepository.java
            │               └── service/
            │                   └── NoteService.java
            └── resources/
                └── application.properties
```

## Configuration

### Backend (notes-server)

Located in `notes-server/src/main/resources/application.properties`:

```properties
spring.application.name=practice

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:notesdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Frontend (notes-client)

The React app runs on port 3000 and communicates with the backend on port 8080.

## API Endpoints

### Backend API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/notes` | Get all notes |
| POST | `/api/notes` | Create a new note |
| DELETE | `/api/notes/{id}` | Delete a note |

### CORS Configuration

The backend is configured to allow CORS requests from `http://localhost:3000` (React frontend).

```java
@CrossOrigin(origins = "http://localhost:3000")
```

## Data Model

### Note
```json
{
    "id": 1,
    "title": "Note Title",
    "content": "Note content goes here"
}
```

## Postman Request Examples

### 1. Get All Notes

**Request:**
```
GET http://localhost:8080/api/notes
```

**Response:**
```json
[
    {
        "id": 1,
        "title": "My First Note",
        "content": "This is my first note"
    }
]
```

### 2. Create a New Note

**Request:**
```
POST http://localhost:8080/api/notes
Content-Type: application/json

{
    "title": "New Note",
    "content": "This is a new note"
}
```

**Response:**
```json
{
    "id": 1,
    "title": "New Note",
    "content": "This is a new note"
}
```

### 3. Delete a Note

**Request:**
```
DELETE http://localhost:8080/api/notes/1
```

**Response:** No content

## Running the Application

### Backend (Spring Boot)

```bash
cd notes_projects/notes-server

# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend (React)

```bash
cd notes_projects/notes-client

# Install dependencies
npm install

# Start the development server
npm start
```

The frontend will open on `http://localhost:3000`

## H2 Console

Access the database console at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:notesdb`
- **Username:** `sa`
- **Password:** (empty)

## Features

### Backend
1. **RESTful API** - Clean REST endpoints
2. **CORS Enabled** - Cross-origin requests from React
3. **JPA Repository** - Database access with Spring Data JPA
4. **In-Memory Database** - H2 for development

### Frontend
1. **Note Form** - Create new notes with title and content
2. **Note List** - Display all notes
3. **Delete Notes** - Remove notes
4. **React Hooks** - Modern React state management

## Code Highlights

### Note Entity (Backend)
```java
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    // getters, setters, constructors
}
```

### Note Controller (Backend)
```java
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {
    @PostMapping
    public Note create(@RequestBody Note note) { ... }
    
    @GetMapping
    public List<Note> getAll() { ... }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { ... }
}
```

### Note Form Component (Frontend)
```jsx
function NoteForm() {
    // Handles note creation form
    // Posts to http://localhost:8080/api/notes
}
```

## Development Notes

- The backend uses H2 in-memory database, so data is lost when the server restarts
- CORS is configured to only allow requests from `http://localhost:3000`
- The React frontend auto-refreshes on file changes (development mode)

