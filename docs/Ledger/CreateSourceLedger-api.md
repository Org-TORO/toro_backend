# Create Source Ledger API

Create a source ledger for an organization and vessel.

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
| `Content-Type` | Yes | `application/json` |
| `Accept` | No | `application/json` |

## Endpoint

```http
POST /api/ledgers/create-source-ledger
```

## Request Body

| Field | Type | Required | Validation | Description |
| --- | --- | --- | --- | --- |
| `organizationId` | `number` | Yes | Must not be `null`. | Organization ID linked to the source ledger. |
| `vesselId` | `number` | Yes | Must not be `null`. | Vessel ID linked to the source ledger. A vessel can have only one source ledger. |
| `sourceType` | `string` | Yes | Must not be blank. Must exist in `SourceType`. | Source ledger type. Accepted values: `VESSEL`, `FARM`. |

Example:

```json
{
  "organizationId": 1,
  "vesselId": 1,
  "sourceType": "VESSEL"
}
```

## Success Response

```http
200 OK
```

Body:

```json
{
  "message": "Create source ledger successfully",
  "data": {
    "id": 1,
    "organizationId": 1,
    "vesselId": 1,
    "sourceType": "VESSEL",
    "createdAt": "2026-09-30T09:00:00Z"
  },
  "timestamp": "2026-09-30T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data.id` | `number` | Created source ledger ID. |
| `data.organizationId` | `number` | Organization ID linked to the created source ledger. |
| `data.vesselId` | `number` | Vessel ID linked to the created source ledger. |
| `data.sourceType` | `string` | Source ledger type. |
| `data.createdAt` | `string` | Creation timestamp in ISO-8601 format. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

## Error Responses

### Invalid Request Body

Returned when required fields are missing, `null`, blank, or `sourceType` is not one of the values in `SourceType`.

```http
400 Bad Request
```

Example:

```json
{
  "message": "Input validation failed",
  "code": "INPUT_VALIDATION_ERROR",
  "errors": {
    "organizationId": "Organization id is required",
    "vesselId": "Vessel id is required",
    "sourceType": "Source type does not exist."
  },
  "timestamp": "2026-09-30T09:00:00Z"
}
```

Possible validation messages:

| Field | Message |
| --- | --- |
| `organizationId` | `Organization id is required` |
| `vesselId` | `Vessel id is required` |
| `sourceType` | `Source type is required` |
| `sourceType` | `Source type does not exist.` |

### Business Validation Failed

Returned when the organization or vessel does not exist, or the vessel already has a source ledger.

```http
400 Bad Request
```

Examples:

```json
{
  "message": "Business validation failed",
  "code": "BUSINESS_VALIDATION_ERROR",
  "errors": "Organization not found.",
  "timestamp": "2026-09-30T09:00:00Z"
}
```

```json
{
  "message": "Business validation failed",
  "code": "BUSINESS_VALIDATION_ERROR",
  "errors": "Vessel not found.",
  "timestamp": "2026-09-30T09:00:00Z"
}
```

```json
{
  "message": "Business validation failed",
  "code": "BUSINESS_VALIDATION_ERROR",
  "errors": "Source ledger already exists.",
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

## Processing Flow

1. Validate the request body with Jakarta Bean Validation.
2. Validate that `sourceType` exists in `SourceType`.
3. Find the organization by `organizationId`.
4. Find the vessel by `vesselId`.
5. Verify that the vessel does not already have a source ledger.
6. Create and save the source ledger.
7. Return the created source ledger summary.

## Curl Example

```bash
curl -X POST http://localhost:8080/api/ledgers/create-source-ledger \
  -H "Authorization: Bearer <accessToken>" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "organizationId": 1,
    "vesselId": 1,
    "sourceType": "VESSEL"
  }'
```

## Notes

- Source ledger uniqueness is enforced by checking whether a ledger already exists for the submitted `vesselId`.
- `sourceType` values are defined in `src/main/java/com/toro/backend/infrastructure/database/enums/SourceType.java`.
- The response body returns only `id`, `organizationId`, `vesselId`, `sourceType`, and `createdAt`.

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/ledger/LedgerController.java` |
| Request DTO | `src/main/java/com/toro/backend/presentation/ledger/request/CreateSourceLedgerRequest.java` |
| Response DTO | `src/main/java/com/toro/backend/presentation/ledger/response/CreateSourceLedgerResponse.java` |
| Use case | `src/main/java/com/toro/backend/application/ledger/create_source_ledger/CreateSourceLedgerUseCase.java` |
| Validation | `src/main/java/com/toro/backend/application/ledger/create_source_ledger/CreateSourceLedgerValidator.java` |
| Repository | `src/main/java/com/toro/backend/infrastructure/database/repository/SourceLedgerRepository.java` |
| Mapper | `src/main/java/com/toro/backend/infrastructure/mapper/ledger/CreateSourceLedgerMapper.java` |
