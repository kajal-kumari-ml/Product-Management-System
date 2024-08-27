# Product-Management-System
Develop a RESTful API service for a unique digital product management system, reflec?ng a simplified version of our B2B SaaS product. The service should manage basic CRUD (Create, Read, Update, Delete) opera?ons for digital products in the system. 

# Product Management API

This project is a Spring Boot-based REST API for managing product information, including adding, updating, deleting, and searching products. The API also includes user authentication endpoints for signup, login, and token refresh.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Running the Service](#running-the-service)
- [Endpoints](#endpoints)
  - [Product Management](#product-management)
  - [User Authentication](#user-authentication)
- [Example Requests and Responses](#example-requests-and-responses)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8.1 or higher
- MongoDB running locally or accessible remotely

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-repo/product-management-api.git
    cd product-management-api
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Set up your MongoDB connection by updating `application.properties` or `application.yml`:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/productdb
    ```

## Running the Service

Start the application by running the following command:

```bash
mvn spring-boot:run
```

The service will be available at `http://localhost:8080/api`.

## Endpoints

### Product Management

- **GET /api/products**  
  Retrieve a list of all products.

- **GET /api/product?productId={id}**  
  Retrieve a product by its ID.

- **POST /api/products**  
  Add a new product.

- **POST /api/products/{productId}**  
  Update an existing product.

- **DELETE /api/products/{productId}**  
  Delete a product by its ID.

- **GET /api/search/name?name={name}**  
  Search for products by name.

- **GET /api/search/price?minPrice={minPrice}&maxPrice={maxPrice}**  
  Search for products by price range.

- **GET /api/search?name={name}&minPrice={minPrice}&maxPrice={maxPrice}**  
  Search for products by name and price range.

### User Authentication

- **POST /api/auth/signup**  
  Register a new user.

- **POST /api/auth/login**  
  Authenticate a user and generate a JWT.

- **POST /api/auth/refresh**  
  Refresh the JWT using a refresh token.

## Example Requests and Responses

### 1. Get All Products

**Request:**
```http
GET /api/products HTTP/1.1
Host: localhost:8080
```

**Response:**
```json
[
  {
    "id": "1",
    "name": "Product A",
    "description": "Description for product A",
    "price": 100.0
  },
  {
    "id": "2",
    "name": "Product B",
    "description": "Description for product B",
    "price": 150.0
  }
]
```

### 2. Add a New Product

**Request:**
```http
POST /api/products HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "Product C",
  "description": "Description for product C",
  "price": 200.0
}
```

**Response:**
```json
{
  "id": "3",
  "name": "Product C",
  "description": "Description for product C",
  "price": 200.0
}
```

### 3. Search by Name

**Request:**
```http
GET /api/search/name?name=Product A HTTP/1.1
Host: localhost:8080
```

**Response:**
```json
[
  {
    "id": "1",
    "name": "Product A",
    "description": "Description for product A",
    "price": 100.0
  }
]
```

### 4. User Signup

**Request:**
```http
POST /api/auth/signup HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  " firstName":"jdss",
  "LastName": "newuser",
  "email":"test@gmail.com"
  "password": "password123"
   "role":"VENDOR"
}
```

**Response:**
```json
{
  "message": "User registered successfully"
}
```

### 5. User Login

**Request:**
```http
POST /api/auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "username": "test@gmail.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---
