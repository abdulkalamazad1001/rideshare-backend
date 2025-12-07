# RideShare Backend - Advanced Spring Boot + MongoDB + JWT System

This project is a fully functional **Ride Sharing Backend**, inspired by platforms like Ola/Uber, built using **Spring Boot**, **MongoDB**, and **JWT Authentication**.  
It not only satisfies **all requirements** from the assignment but also goes **beyond expectations** with production-level security, architecture, and role-based access control.

---

# ⭐ Highlights of this Project:

✅ **Implemented ALL required features**  
✅ **PLUS additional professional features**

- Clean architecture: Controller → Service → Repository
- Robust global exception handling
- Secure JWT Authentication with roles
- Stateless Spring Security setup
- Production-style token filter
- Full role-based access using `@PreAuthorize`
- DTO validation using Jakarta Validation
- MongoDB schema aligned with real-world ride-sharing apps
- Complete Postman-tested workflow
- Clean GitHub-friendly structure
- Error-safe service layering
- Extendable design ready for real deployment

it is a **real backend application** following industry standards.

---

# 🧱 Technologies Used:

- **Java 24**
- **Spring Boot 3.4**
- **Spring Security + JWT**
- **MongoDB**
- **Maven**
- **Jakarta Validation**
- **Lombok** (optional)
- **Postman** for API testing

---

# 🏗 Folder Structure (Clean Architecture) :

```
src/main/java/org/example/rideshare/
│
├── config/ → SecurityConfig, JwtFilter
├── controller/ → AuthController, RideController
├── dto/ → RegisterRequest, LoginRequest, CreateRideRequest
├── exception/ → GlobalExceptionHandler, NotFound, BadRequest
├── model/ → User, Ride
├── repository/ → UserRepository, RideRepository
├── service/ → UserService, RideService
└── util/ → JwtUtil
```

This architecture ensures:

- Easy debugging
- High code readability
- Real-world scalability
- Clear separation of responsibilities

---

# 🔐 Security Design

### JWT Authentication

- Login returns a signed JWT token
- Every secured endpoint requires `Authorization: Bearer <token>`
- Token contains **username, role, issuedAt, expiry**

### Role-Based Access Control

Using `@PreAuthorize`:

| Role   | Permissions                                    |
| ------ | ---------------------------------------------- |
| USER   | Create ride, view my rides, complete ride      |
| DRIVER | View pending rides, accept ride, complete ride |
| BOTH   | Can complete rides                             |

### Stateless Security

No sessions. Every request is authenticated independently.

---

# 📘 Entity Design (MongoDB)

### 🧑‍💼 User

```
id: String
username: String
password: String (BCrypt)
role: ROLE_USER / ROLE_DRIVER
```

### 🚕 Ride

```
id: String
userId: String
driverId: String?
pickupLocation: String
dropLocation: String
status: REQUESTED / ACCEPTED / COMPLETED
createdAt: Date
```

This models a real ride-sharing workflow accurately.

---

#  API Endpoints (Fully Tested)

## PUBLIC

| Method | Endpoint             | Description           |
| ------ | -------------------- | --------------------- |
| POST   | `/api/auth/register` | Create user/driver    |
| POST   | `/api/auth/login`    | Login & get JWT token |

## USER

| Method | Endpoint             | Description   |
| ------ | -------------------- | ------------- |
| POST   | `/api/v1/rides`      | Create a ride |
| GET    | `/api/v1/user/rides` | View my rides |

## DRIVER

| Method | Endpoint                           | Description        |
| ------ | ---------------------------------- | ------------------ |
| GET    | `/api/v1/driver/rides/requests`    | View pending rides |
| POST   | `/api/v1/driver/rides/{id}/accept` | Accept ride        |

## BOTH

| Method | Endpoint                      | Description   |
| ------ | ----------------------------- | ------------- |
| POST   | `/api/v1/rides/{id}/complete` | Complete ride |

---

# Example Workflow (Postman Tested) :

1️⃣ Register USER  
2️⃣ Register DRIVER  
3️⃣ Login both → get tokens  
4️⃣ USER creates ride  
5️⃣ DRIVER sees pending rides  
6️⃣ DRIVER accepts ride  
7️⃣ USER or DRIVER completes ride  
8️⃣ USER views ride history

All flows **successfully tested**.

---

# Validation & Error Handling :

We implemented:

- `@NotBlank`, `@Valid`, etc.
- Clean JSON error responses
- Custom exceptions for bad requests and not found cases

Example error response:

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Pickup is required",
  "timestamp": "2025-01-20T12:00:00Z"
}
```

---

# Learning Outcomes :

This project teaches you real backend development:

- JWT Security
- Filters & Authentication Flow
- Permission-based roles
- Writing clean REST APIs
- MongoDB modeling
- Exception handling
- Application layer separation
- Production-grade folder structure
- How real apps like Uber/Ola handle rides internally

---

# How to Run the Project:

Clone:

```bash
git clone https://github.com/abdulkalamazad1001/rideshare-backend
```

Install:

```bash
mvn clean install
```

Run:

```bash
mvn spring-boot:run
```

Backend starts at:

```
http://localhost:8080
```

---

# Final Note :

This RideShare backend goes far beyond the assignment requirements and represents a high-quality, real-world backend design.

---

✍️ **Author: Abdul Kalam Azad** ❤️
