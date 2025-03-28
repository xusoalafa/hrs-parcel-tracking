# HRS Parcel Tracking API

## Overview
This API provides endpoints for managing guest check-ins, check-outs, and parcel statuses. It allows receptionists to track guest statuses and parcel availability efficiently.

## Base URL
```
http://localhost:8080
```

## API Endpoints

### 1. Retrieve All Guests
**Endpoint:** `GET /guests`

**Description:** Returns a list of all guests.

#### Response Example:
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "1234567890",
    "roomNumber": "101",
    "checkedIn": true,
    "checkedOut": false,
    "parcelAvailable": false
  }
]
```

### 2. Insert Guest Information
**Endpoint:** `POST /guests`

**Description:** Inserts guest information when they arrive.

#### Request Example:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "roomNumber": "101"
}
```

#### Response Example:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "roomNumber": "101",
  "checkedIn": false,
  "checkedOut": false,
  "parcelAvailable": false
}
```

### 3. Update Parcel Status
**Endpoint:** `PATCH /guests/{id}/update-parcel-status`

**Description:** Updates parcel availability when the receptionist receives or hands over a parcel.

#### Request Example:
```json
{
  "parcelAvailable": true
}
```

### 4. Check-in a Guest
**Endpoint:** `PATCH /guests/{id}/check-in`

**Description:** Marks a guest as checked in.

#### Request Example:
```json
{
  "checkedIn": true
}
```

### 5. Check-out a Guest
**Endpoint:** `PATCH /guests/{id}/check-out`

**Description:** Marks a guest as checked out. Before allowing checkout, the system ensures that all associated parcels have been returned to the guest and their status has been updated.

**Error Handling:** If the guest has an available parcel but attempts to check out, the system will return an error:
```json
{
  "The guest has an available parcel. Return the parcel to the guest and update its status before checkout."
}
```
#### Request Example:
```json
{
  "checkedOut": true
}
```

### 6. Retrieve Checked-in Guests
**Endpoint:** `GET /guests/available-guests?name={guestName}`

**Description:** Returns a list of guests who have checked in. Supports filtering by name.

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```sh
   cd guest-management-api
   ```
3. Start the application:
   ```sh
   mvn spring-boot:run
   ```

## API Documentation
To view the API documentation, navigate to:
```
http://localhost:8080/swagger-ui.html
```

## Contact
For any questions or support, please reach out to the development team.

