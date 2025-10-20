# Entropy Beacon

**Entropy Beacon** is a small, autonomous Spring Boot application designed to generate and log secure, timestamped random values. It serves as a public facing Random Number Generator (RNG) API while maintaining a historical log of all generated beacons.

---

## Features

* **Secure Randomness:** Generates 128-bit cryptographically secure random values using Java’s `SecureRandom`.
* **Persistent Logging:** Appends each new beacon (timestamp + value) to a structured JSON log file (`beacon.log`).
* **REST API Endpoint:** Exposes the most recently generated beacon via a public API endpoint.
* **Fully Automated:** Uses a GitHub Actions workflow to run daily, generate a new beacon, and commit the updated log file back to the repository.

---

## How It Works

The application is built with **Java** and **Spring Boot** and operates through two main components:

1. **Core Application Logic**
2. **Automation Workflow**

### Core Application

* **Beacon Generation:** A Spring `@Component` (`BeaconGenerator`) implements `CommandLineRunner`. When the application starts, it executes its `run()` method once.
* **Data Logging:** The method generates a 128-bit random value, pairs it with a UTC timestamp, formats it as a JSON object, and appends it to `beacon.log`.
* **API Service:** A `@RestController` (`BeaconController`) provides an HTTP endpoint that retrieves the latest beacon value from the `BeaconGenerator` service and serves it as JSON.
* **Graceful Shutdown:** After completing its task, the Spring application shuts down automatically to ensure proper termination — useful for CI/CD environments like GitHub Actions.

### Automation Workflow (`.github/workflows/auto-commit.yml`)

* **Scheduled Trigger:** Runs automatically once a day (at 02:00 UTC). It can also be triggered manually.
* **Environment Setup:** Checks out the repository code and sets up a Java 17 environment.
* **Application Execution:** Executes the Spring Boot application using `mvn spring-boot:run`, which generates and logs one new beacon before exiting.
* **Automated Commit:** Uses the `stefanzweifel/git-auto-commit-action` to detect changes in `beacon.log`, commit them (e.g., `feat: Generate new entropy beacon for 2025-10-20`), and push to the main branch.

---

## Setup and Build

### Prerequisites

Before running the project, make sure you have the following installed:

* **Java 17** or newer
* **Maven 3.8+**
* **Git** (for repository management)

### Cloning the Repository

```bash
git clone https://github.com/your-username/entropy-beacon.git
cd entropy-beacon
```

### Building the Application

You can build the project using Maven:

```bash
mvn clean package
```

The compiled `.jar` file will be available in the `target/` directory.

### Running the Application

To start the application:

```bash
mvn spring-boot:run
```

Alternatively, run the packaged `.jar` file:

```bash
java -jar target/entropy-beacon-0.0.1-SNAPSHOT.jar
```

This starts the web server and generates one random beacon entry.
To enable continuous generation, uncomment or re-enable the `@Scheduled` annotation in `BeaconGenerator.java`.

---

## Usage

### Accessing the API

Once the application is running, you can access the API endpoint via browser or `curl`:

**URL:**

```
http://localhost:8080/api/latest-beacon
```

**Example with curl:**

```bash
curl http://localhost:8080/api/latest-beacon
```

**Example Response:**

```json
{
  "timestamp": "2025-10-20T14:30:00.123456Z",
  "value": "a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6"
}
```

---

## Project Structure

```
.
├── .github/workflows/auto-commit.yml  # GitHub Actions automation script
├── src/main/java/com/entropybeacon/
│   ├── BeaconController.java          # REST Controller for the API
│   ├── BeaconDTO.java                 # Data Transfer Object for the API response
│   ├── BeaconGenerator.java           # Core service, runs on startup
│   └── EntropyBeaconApplication.java  # Main Spring Boot application class
├── beacon.log                         # Log file for generated beacons
└── pom.xml                            # Maven project configuration
```

---

## License

This project is open-source and available under the [MIT License](LICENSE).

## Author

Muhammad Owais Javed