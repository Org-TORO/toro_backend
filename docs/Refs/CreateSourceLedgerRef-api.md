# Create Source Ledger Ref API

Reference data API used by the create source ledger flow.

Default local base URL:

```text
http://localhost:8080
```

## Access

This endpoint requires authentication. Send a JWT access token in the `Authorization` header.

## Headers

| Header | Required | Value |
| --- | --- | --- |
| `Authorization` | Yes | `Bearer <accessToken>` |
| `Accept` | No | `application/json` |

## Endpoint

```http
GET /api/refs/create-source-ledger/refs
```

## Query Parameters

None.

## Success Response

```http
200 OK
```

Body:

```json
{
  "message": "Get create source ledger refs successfully",
  "data": {
    "organizationId": 1,
    "organizationName": "TORO Supplier",
    "vessels": [
      {
        "id": 1,
        "registrationNumber": "VN-BD-0001",
        "fishingMethod": "Longline",
        "captainName": "Nguyen Van An",
        "fishingLicense": "FL-BD-0001",
        "imoNumber": "IMO1000001"
      }
    ]
  },
  "timestamp": "2026-09-30T09:00:00Z"
}
```

## Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data` | `object` | Reference data for creating a source ledger. |
| `data.organizationId` | `number` | Current authenticated user's organization ID. |
| `data.organizationName` | `string` | Current authenticated user's organization name. |
| `data.vessels` | `array` | List of vessel reference objects. |
| `data.vessels[].id` | `number` | Vessel ID. |
| `data.vessels[].registrationNumber` | `string` | Vessel registration number. |
| `data.vessels[].fishingMethod` | `string` | Vessel fishing method. |
| `data.vessels[].captainName` | `string` | Vessel captain name. |
| `data.vessels[].fishingLicense` | `string` | Vessel fishing license. |
| `data.vessels[].imoNumber` | `string` | Vessel IMO number. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

## Error Responses

### Business Validation Failed

Returned when the authenticated user does not have a current organization membership.

```http
400 Bad Request
```

Example:

```json
{
  "message": "Business validation failed",
  "code": "BUSINESS_VALIDATION_ERROR",
  "errors": "Current organization not found.",
  "timestamp": "2026-09-30T09:00:00Z"
}
```

### Unauthenticated

Returned when the request does not include a valid access token.

```http
401 Unauthorized
```

### Forbidden

Returned when the authenticated user is not allowed to access the API.

```http
403 Forbidden
```

## Curl Example

```bash
curl -X GET http://localhost:8080/api/refs/create-source-ledger/refs \
  -H "Authorization: Bearer <accessToken>" \
  -H "Accept: application/json"
```

## Notes

- This endpoint returns reference data with `SuccessResponse`.
- The response data is shaped with anonymous objects instead of a dedicated response DTO.
- `organizationId` and `organizationName` are resolved from the current authenticated user's first organization membership.
- `vessels` contains all vessels available in the vessel repository.

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/refs/create_source_ledger/CreateSourceLedgerRefController.java` |
| Vessel repository | `src/main/java/com/toro/backend/infrastructure/database/repository/VesselRepository.java` |
| Organization member repository | `src/main/java/com/toro/backend/infrastructure/database/repository/OrganizationMemberRepository.java` |
| Success response envelope | `src/main/java/com/toro/backend/infrastructure/api/SuccessResponse.java` |
