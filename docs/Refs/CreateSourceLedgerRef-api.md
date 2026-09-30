# Create Source Ledger Ref APIs

Reference data APIs used by the create source ledger flow.

Default local base URL:

```text
http://localhost:8080
```

## Access

These endpoints require authentication. Send a JWT access token in the `Authorization` header.

## Headers

| Header | Required | Value |
| --- | --- | --- |
| `Authorization` | Yes | `Bearer <accessToken>` |
| `Accept` | No | `application/json` |

## Get Vessels

Return vessels available for source ledger creation.

### Endpoint

```http
GET /api/refs/create-source-ledger/get-vessels
```

### Query Parameters

None.

### Success Response

```http
200 OK
```

Body:

```json
{
  "message": "Get vessels successfully",
  "data": [
    {
      "id": 1,
      "registrationNumber": "VN-BD-0001",
      "fishingMethod": "Longline",
      "captainName": "Nguyen Van An",
      "fishingLicense": "FL-BD-0001",
      "imoNumber": "IMO1000001"
    }
  ],
  "timestamp": "2026-09-29T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data` | `array` | List of vessel reference objects. |
| `data[].id` | `number` | Vessel ID. |
| `data[].registrationNumber` | `string` | Vessel registration number. |
| `data[].fishingMethod` | `string` | Vessel fishing method. |
| `data[].captainName` | `string` | Vessel captain name. |
| `data[].fishingLicense` | `string` | Vessel fishing license. |
| `data[].imoNumber` | `string` | Vessel IMO number. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

### Curl Example

```bash
curl -X GET http://localhost:8080/api/refs/create-source-ledger/get-vessels \
  -H "Authorization: Bearer <accessToken>" \
  -H "Accept: application/json"
```

## Get Main Staffs

Return organization members whose organization role is `MAIN_STAFF`.

### Endpoint

```http
GET /api/refs/create-source-ledger/get-main-staffs
```

### Query Parameters

| Parameter | Type | Required | Description |
| --- | --- | --- | --- |
| `search` | `string` | No | Single search value matched against main staff `fullName` or `email`. Matching is case-insensitive and partial. Blank values are ignored. |

### Success Response

```http
200 OK
```

Body:

```json
{
  "message": "Get main staffs successfully",
  "data": [
    {
      "id": 2,
      "userId": 3,
      "fullName": "System Main Staff",
      "email": "mainstaff@example.com",
      "phoneNumber": "0909111111",
      "organizationId": 1,
      "organizationName": "TORO Supplier"
    }
  ],
  "timestamp": "2026-09-29T09:00:00Z"
}
```

### Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `message` | `string` | Human-readable result message. |
| `data` | `array` | List of main staff reference objects. |
| `data[].id` | `number` | Organization member ID. Use this value when assigning source ledger work to a main staff member. |
| `data[].userId` | `number` | User ID linked to the organization member. |
| `data[].fullName` | `string` | Main staff user's full name. |
| `data[].email` | `string` | Main staff user's email address. |
| `data[].phoneNumber` | `string` | Main staff user's phone number. |
| `data[].organizationId` | `number` | Organization ID linked to the main staff member. |
| `data[].organizationName` | `string` | Organization name linked to the main staff member. |
| `timestamp` | `string` | Response timestamp in ISO-8601 format. |

### Curl Examples

Get all main staffs:

```bash
curl -X GET http://localhost:8080/api/refs/create-source-ledger/get-main-staffs \
  -H "Authorization: Bearer <accessToken>" \
  -H "Accept: application/json"
```

Search by full name or email:

```bash
curl -X GET "http://localhost:8080/api/refs/create-source-ledger/get-main-staffs?search=mainstaff" \
  -H "Authorization: Bearer <accessToken>" \
  -H "Accept: application/json"
```

## Error Responses

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

## Notes

- These endpoints return reference data with `SuccessResponse`.
- The controller calls repositories directly and shapes the response with anonymous objects.
- `get-main-staffs` searches only members with `organizationRole = MAIN_STAFF`.

## Related Source Files

| Concern | File |
| --- | --- |
| Controller | `src/main/java/com/toro/backend/presentation/refs/create_source_ledger/CreateSourceLedgerRefController.java` |
| Vessel repository | `src/main/java/com/toro/backend/infrastructure/database/repository/VesselRepository.java` |
| Organization member repository | `src/main/java/com/toro/backend/infrastructure/database/repository/OrganizationMemberRepository.java` |
| Success response envelope | `src/main/java/com/toro/backend/infrastructure/api/SuccessResponse.java` |
