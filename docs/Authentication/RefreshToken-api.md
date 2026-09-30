# Refresh Token API

Issue a new JWT access token and a rotated opaque refresh token from an existing refresh token. On success, the API returns authenticated user information.

## Endpoint

```http
POST /api/auth/refresh-token
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
| `refreshToken` | `string` | Yes | Must not be blank. | Opaque refresh token previously returned by login or refresh-token APIs. |

Example:

```json
{
  "refreshToken": "<refresh-token>"
}
```

## Success Response

```http
200 OK
```

Body:

```json
{
  "message": "Refresh token successfully",
  "data": {
    "accessToken": "<jwt-access-token>",
    "refreshToken": "<new-refresh-token>",
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
  "timestamp": "2026-09-30T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data.accessToken` | `string` | JWT access token. Use it as `Authorization: Bearer <accessToken>` for protected APIs. |
| `data.refreshToken` | `string` | New opaque refresh token. The previous refresh token is replaced. |
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

Returned when `refreshToken` is blank or missing.

```http
400 Bad Request
```

Example:

```json
{
  "message": "Input validation failed",
  "code": "INPUT_VALIDATION_ERROR",
  "errors": {
    "refreshToken": "Refresh token is required"
  },
  "timestamp": "2026-09-30T09:00:00Z"
}
```

### Invalid Refresh Token

Returned when the refresh token is missing, unknown, revoked, expired, or not attached to an existing user.

```http
401 Unauthorized
```

Body:

```json
{
  "message": "Invalid refresh token",
  "code": "REFRESH_TOKEN_ERROR",
  "errors": "Invalid refresh token",
  "timestamp": "2026-09-30T09:00:00Z"
}
```

When the login session is expired, the response uses the same code and status with `Login Session is Expired` as the message and errors value.

## Processing Flow

1. Validate the request body with Jakarta Bean Validation.
2. Hash the submitted opaque refresh token with SHA-256.
3. Find the login session by hashed refresh token.
4. Verify the session has a user, is not revoked, and is not expired.
5. Generate a new signed JWT access token.
6. Generate a new opaque refresh token and store only its SHA-256 hash.
7. Load the user's first organization membership, when present.
8. Return the new access token, new refresh token, and authenticated user information.

## Token Behavior

Refresh token rotates both tokens on the existing login session.

| Token | Behavior |
| --- | --- |
| Access token | Reissued as a signed JWT with a new access-token expiry. |
| Refresh token | Replaced by a new opaque refresh token. The backend stores only the SHA-256 hash of the new token. |

## Cookie Behavior

This endpoint does not read `refreshToken` from cookies and does not set a `Set-Cookie` response header.

## Curl Example

```bash
curl -X POST http://localhost:8080/api/auth/refresh-token \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "refreshToken": "<refresh-token>"
  }'
```

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/authentication/AuthenticationController.java` |
| Request DTO | `src/main/java/com/toro/backend/presentation/authentication/request/RefreshTokenRequest.java` |
| Response DTO | `src/main/java/com/toro/backend/presentation/authentication/response/RefreshTokenResponse.java` |
| Use case | `src/main/java/com/toro/backend/application/authentication/refresh_token/RefreshTokenUseCase.java` |
| Validation | `src/main/java/com/toro/backend/application/authentication/refresh_token/RefreshTokenValidator.java` |
| Error envelope | `src/main/java/com/toro/backend/infrastructure/exception/GlobalExceptionHandler.java` |
