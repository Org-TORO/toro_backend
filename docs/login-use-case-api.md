# Login Use Case API

Authenticate a user with email and password, then issue a new access token and refresh token.

## Endpoint

```http
POST /api/auth/login
```

Default local base URL:

```text
http://localhost:8080
```

## Authentication

This endpoint is public. Do not send an `Authorization` header.

## Headers

| Header | Required | Value |
| --- | --- | --- |
| `Content-Type` | Yes | `application/json` |
| `Accept` | No | `application/json` |

## Request Body

| Field | Type | Required | Validation | Description |
| --- | --- | --- | --- | --- |
| `email` | `string` | Yes | Must not be blank. Must be a valid email address. | User email address. |
| `password` | `string` | Yes | Must not be blank. | User password. |

Example:

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

## Success Response

Status:

```http
200 OK
```

Body:

```json
{
  "message": "Login successfully",
  "data": {
    "accessToken": "<jwt-access-token>",
    "refreshToken": "<refresh-token>"
  },
  "timestamp": "2026-09-29T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data.accessToken` | `string` | JWT access token. Use it as `Authorization: Bearer <accessToken>` for protected APIs. |
| `data.refreshToken` | `string` | Opaque refresh token used to request new tokens. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

## Error Responses

### Invalid Request Body

Returned when required fields are blank or the email is not a valid email address.

Status:

```http
400 Bad Request
```

Example:

```json
{
  "message": "Input validation failed",
  "code": "INPUT_VALIDATION_ERROR",
  "errors": {
    "email": "Email is not valid",
    "password": "Password is required"
  },
  "timestamp": "2026-09-29T09:00:00Z"
}
```

Possible validation messages:

| Field | Message |
| --- | --- |
| `email` | `Email is required` |
| `email` | `Email is not valid` |
| `password` | `Password is required` |

### Invalid Credentials

Returned when the email does not exist or the password does not match.

Status:

```http
400 Bad Request
```

Body:

```json
{
  "message": "Business validation failed",
  "code": "BUSINESS_VALIDATION_ERROR",
  "errors": "Invalid username or password.",
  "timestamp": "2026-09-29T09:00:00Z"
}
```

## Token Behavior

On successful login, the backend:

1. Generates a JWT access token.
2. Generates an opaque refresh token.
3. Stores only the SHA-256 hash of the refresh token in the login session table.
4. Creates a login session for the user if one does not exist.
5. Rotates the existing login session tokens if the user already has a login session.

Default token lifetime in the `dev` profile:

| Token | Lifetime |
| --- | --- |
| Access token | 15 minutes |
| Refresh token | 7 days |

## Curl Example

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/authentication/AuthenticationController.java` |
| Request DTO | `src/main/java/com/toro/backend/presentation/authentication/request/LoginRequest.java` |
| Response DTO | `src/main/java/com/toro/backend/presentation/authentication/response/LoginResponse.java` |
| Use case | `src/main/java/com/toro/backend/application/authentication/login/LoginUseCase.java` |
| Validation | `src/main/java/com/toro/backend/application/authentication/login/LoginValidator.java` |
| Error envelope | `src/main/java/com/toro/backend/infrastructure/exception/GlobalExceptionHandler.java` |
