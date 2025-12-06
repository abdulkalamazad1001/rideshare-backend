# RideShare Backend

This repository contains a compact, production-minded backend blueprint for a ride-hailing app (think small Ola/Uber).
It uses Spring Boot for REST APIs, Spring Security + JWT for authentication/authorization, and MongoDB for persistence.

This README is a standalone developer reference: architecture, folder layout, security model, core APIs with examples, and local
run instructions.

---

## Quick summary

- Stack: Java + Spring Boot, Spring Security (JWT), MongoDB.
- Roles: `ROLE_USER` (rider) and `ROLE_DRIVER` (driver).
- Main collections: `users`, `rides`.
- Typical flow: register → login → request ride → driver accepts → complete.

---

## Project layout (src/main/java/org/example/rideshare)

- `config` — Security and JWT filter (`SecurityConfig.java`, `JwtFilter.java`).
- `controller` — REST controllers (`AuthController.java`, `RideController.java`).
- `dto` — Request/response DTOs (`RegisterRequest`, `LoginRequest`, `CreateRideRequest`).
- `service` — Business rules and orchestration (`UserService`, `RideService`).
- `repository` — MongoDB repositories (`UserRepository`, `RideRepository`).
- `model` — Domain entities persisted to MongoDB (`User`, `Ride`).
- `exception` — Custom exceptions and `GlobalExceptionHandler` for consistent errors.
- `util` — Helpers such as `JwtUtil` for token tasks.

This architecture keeps controllers thin and business logic testable.

---

## Data model (high level)

- `users` fields (example): `_id`, `username`, `passwordHash`, `role`.
- `rides` fields (example): `_id`, `userId`, `driverId`, `pickupLocation`, `dropLocation`, `status` (`REQUESTED`/`ACCEPTED`/`COMPLETED`), `createdAt`.

Use indexes for frequent queries (e.g., `status` for pending rides).

---

## Security & roles

- Authentication: JWT tokens issued at login. Tokens signed using `jwt.secret` and include username + role claims.
- Authorization: Role checks via `@PreAuthorize` annotations on controller methods.
- Public endpoints: registration and login. All ride APIs are protected.

Token header example:

```
Authorization: Bearer <JWT_TOKEN>
```

Unauthorized or invalid token → `403 Forbidden`.

---

## Core API reference (quick)

Authentication

- POST `/auth/register`

  - Body: `{"username":"alice","password":"pass","role":"USER"}`
  - Returns: 201 Created (user created)

- POST `/auth/login`
  - Body: `{"username":"alice","password":"pass"}`
  - Returns: `{ "token": "<jwt>" }`

Ride lifecycle (protected)

- POST `/rides` (USER)

  - Body: `{ "pickupLocation":"MG Road","dropLocation":"Airport" }`
  - Creates a ride with `status=REQUESTED`.

- GET `/rides/pending` (DRIVER)

  - Lists rides with `status=REQUESTED`.

- POST `/rides/{id}/accept` (DRIVER)

  - Sets `status=ACCEPTED` and `driverId`.

- POST `/rides/{id}/complete` (USER or DRIVER)

  - Sets `status=COMPLETED`.

- GET `/rides/history` (USER)
  - Returns rides for the authenticated user.

Responses follow a consistent JSON error format from `GlobalExceptionHandler`.

---

## Example: quick curl flow

Register:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"rider1","password":"pass","role":"USER"}'
```

Login (get token):

```bash
curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"rider1","password":"pass"}'
```

Create ride (replace `<TOKEN>`):

```bash
curl -X POST http://localhost:8080/rides \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"pickupLocation":"Station","dropLocation":"Airport"}'
```

---

## Run locally (minimal)

1. Ensure Java 17+ is installed.
2. Start MongoDB locally or use a cloud connection.
3. Set configuration (example `application.properties`):

```
spring.data.mongodb.uri=mongodb://localhost:27017/rideshare
jwt.secret=replace_this_with_a_strong_secret
jwt.expiration-ms=3600000
```

4. Build and run:

```powershell
mvn clean package; java -jar target/*.jar
```

5. Use Postman or curl to exercise the APIs.

---

## Production pointers

- Store `jwt.secret` in a secrets manager or environment variable.
- Use HTTPS in production.
- Enable rate limiting and request logging.
- Add observability: metrics (Prometheus), health checks, centralized logs.

---

## Next improvements (recommended)

- Add Postman collection and a `docs/` folder with full API specs (OpenAPI/Swagger).
- Add Dockerfile and `docker-compose.yml` to include MongoDB for easy local dev.
- Add tests (unit & integration) and CI to run them automatically.

---

Made by Abdul Kalam Azad ❤️
