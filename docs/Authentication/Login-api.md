# Login API

Authenticate a user with email and password. On success, the API returns a JWT access token and an opaque refresh token.

## Endpoint

```http
POST /api/auth/login
```

Default local base URL:

```text
http://localhost:8080
```

## Access

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

```http
200 OK
```

Body:

```json
{
  "message": "Login successfully",
  "data": {
    "accessToken": "<jwt-access-token>",
    "refreshToken": "<refresh-token>",
    "user": {
      "id": 1,
      "fullName": "Example User",
      "email": "user@example.com",
      "phoneNumber": "0900000000",
      "role": "ADMIN",
      "memberId": null,
      "organizationRole": null,
      "organizationId": null
    }
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
| `data.user` | `object` | Authenticated user information. |
| `data.user.id` | `number` | Authenticated user ID. |
| `data.user.fullName` | `string` | Authenticated user's full name. |
| `data.user.email` | `string` | Authenticated user's email address. |
| `data.user.phoneNumber` | `string` | Authenticated user's phone number. |
| `data.user.role` | `string` | Authenticated user's application role. |
| `data.user.memberId` | `number \| null` | Authenticated user's organization member ID, or `null` for admin users. |
| `data.user.organizationRole` | `string \| null` | Authenticated user's organization role, or `null` for admin users. |
| `data.user.organizationId` | `number \| null` | Authenticated user's organization ID, or `null` for admin users. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

## Error Responses

### Invalid Request Body

Returned when required fields are blank or the email is not a valid email address.

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

## Processing Flow

1. Validate the request body with Jakarta Bean Validation.
2. Find the user by `email`.
3. Compare the submitted password with the stored password hash.
4. Generate the access token expiry and refresh token expiry.
5. Generate a signed JWT access token.
6. Generate an opaque refresh token.
7. Hash the refresh token with SHA-256 before storing it.
8. Create or update the user's login session.
9. Return the raw access token, raw refresh token, and authenticated user information to the client.

## Token Behavior

On successful login, the backend:

| Token | Format | Usage |
| --- | --- | --- |
| Access token | Signed JWT | Sent as `Authorization: Bearer <accessToken>` to protected APIs. |
| Refresh token | Opaque random string | Sent to refresh/bootstrap token APIs when the access token expires. |

The backend stores only the SHA-256 hash of the refresh token. If the user already has a login session, login rotates the tokens on the existing session instead of creating multiple active login session records.

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

## Notes

- The response body returns the refresh token directly. This endpoint does not set the refresh token cookie.
- Invalid email and invalid password return the same error message: `Invalid username or password.`
- A successful login clears `revokedReason` and `revokedAt` on the login session.
- The authenticated user payload does not include `passwordHash`.

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/authentication/AuthenticationController.java` |
| Request DTO | `src/main/java/com/toro/backend/presentation/authentication/request/LoginRequest.java` |
| Response DTO | `src/main/java/com/toro/backend/presentation/authentication/response/LoginResponse.java` |
| Use case | `src/main/java/com/toro/backend/application/authentication/login/LoginUseCase.java` |
| Validation | `src/main/java/com/toro/backend/application/authentication/login/LoginValidator.java` |
| Error envelope | `src/main/java/com/toro/backend/infrastructure/exception/GlobalExceptionHandler.java` |
