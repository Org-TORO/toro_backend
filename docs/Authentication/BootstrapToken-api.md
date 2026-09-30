# Bootstrap Token API

Bootstrap an authenticated client session from an existing refresh token. On success, the API returns a JWT access token, the refresh token from the request body, and authenticated user information.

## Endpoint

```http
POST /api/auth/bootstrap-token
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
  "message": "Bootstrap token successfully",
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
  "timestamp": "2026-09-30T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data.accessToken` | `string` | JWT access token. Use it as `Authorization: Bearer <accessToken>` for protected APIs. |
| `data.refreshToken` | `string` | Same opaque refresh token sent in the request body. Bootstrap does not rotate the refresh token. |
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

## Processing Flow

1. Validate the request body with Jakarta Bean Validation.
2. Hash the submitted opaque refresh token with SHA-256.
3. Find the login session by hashed refresh token.
4. Verify the session has a user, is not revoked, and is not expired.
5. Generate a signed JWT access token using the current session access-token expiry.
6. Load the user's first organization membership, when present.
7. Return the access token, request refresh token, and authenticated user information.

## Token Behavior

Bootstrap token does not rotate the refresh token and does not create a new login session.

| Token | Behavior |
| --- | --- |
| Access token | Reissued as a signed JWT using the login session's current access-token expiry. |
| Refresh token | Read from the JSON request body and returned in the JSON response body unchanged. |

## Cookie Behavior

This endpoint does not read `refreshToken` from cookies and does not set a `Set-Cookie` response header.

## Curl Example

```bash
curl -X POST http://localhost:8080/api/auth/bootstrap-token \
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
| Request DTO | `src/main/java/com/toro/backend/presentation/authentication/request/BootstrapTokenRequest.java` |
| Response DTO | `src/main/java/com/toro/backend/presentation/authentication/response/BootstrapTokenResponse.java` |
| Use case | `src/main/java/com/toro/backend/application/authentication/bootstrap_token/BootstrapTokenUseCase.java` |
| Validation | `src/main/java/com/toro/backend/application/authentication/bootstrap_token/BootstrapTokenValidator.java` |
| Error envelope | `src/main/java/com/toro/backend/infrastructure/exception/GlobalExceptionHandler.java` |
