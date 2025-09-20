# Golden Raspberry Awards

A Spring Boot API to retrieve the producer with the longest interval between two consecutive awards, and the one who achieved two awards the fastest.

## Project Structure
- src/main/java - Application source code
- src/test/java - Unit tests
- src/main/resources/Movielist.csv - Movie data

## Prerequisites

- Java 21
- Maven 3.8+
- Git

## Setup

Clone the repository:

```sh
git clone https://github.com/ehs90/outsera.git
cd outsera
```
## Build & Run
`mvn clean install`

`mvn spring-boot:run`

The award interval API will be available at http://localhost:8080/intervals

The API documentation is available at src/main/resources/swagger.yml

## Testing

`mvn test`

