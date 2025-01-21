# URL Short Application

This application allows users to shorten URLs, redirect to the original URL using the shortened version, and view a ranking of the most accessed shortened URLs. It uses Spring Boot for the backend and H2 as an in-memory database.

## Features

1. **Shorten URL**: Generate a shortened URL for an original URL.
2. **Redirect**: Redirect from a shortened URL to the original URL.
3. **Ranking**: View the top 10 most accessed shortened URLs.

## Endpoints

### 1. `POST /api/create`

This endpoint generates a shortened URL for a given original URL. If the URL has already been shortened before, it returns the existing shortened URL.

#### Request:
- **Parameters**: 
  - `url`: The original URL to be shortened (e.g., `https://example.com`).

#### Response:
- **Status**: 201 Created
- **Body**: The original URL and its corresponding shortened URL.

#### Example:
```bash
POST /api/create?url=https://example.com
```
Response:
```json
{
  "urlOriginal": "https://example.com",
  "urlShort": "https://abc123.com"
}
```

---

### 2. `GET /api/find`

This endpoint redirects from a shortened URL to the corresponding original URL.

#### Request:
- **Parameters**:
    - `url`: The shortened URL to redirect (e.g., `https://abc123.com`).

#### Response:
- **Status**: 200 OK
- **Header**: `Location`: The original URL.

#### Example:
```bash
GET /api/find?url=https://abc123.com
```
Response:
```http
HTTP/1.1 200 OK
Location: https://example.com
```

---

### 3. `GET /api/ranking`

This endpoint returns a ranking of the top 10 most accessed shortened URLs.

#### Response:
- **Status**: 200 OK
- **Body**: A list of the top 10 most accessed URLs along with the number of times they have been accessed.

#### Example:
```bash
GET /api/ranking
```
Response:
```json
[
  {
    "url": "https://xyz789.com",
    "count": 150
  },
  {
    "url": "https://abc123.com",
    "count": 120
  }
]
```

## Technologies Used

- **Spring Boot**: Framework for building the backend.
- **H2 Database**: In-memory database for storing URLs and access data.
- **SHA-256**: For generating unique short URL hashes.
- **UUID**: To ensure uniqueness in the short URL generation process.

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/viniciusmecosta/UrlShort
   ```

2. Navigate to the project directory:
   ```bash
   cd UrlShort
   ```

3. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

5. The application will be available at `http://localhost:8080`.
