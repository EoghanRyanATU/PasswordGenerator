### Student Name: Eoghan Ryan
### Student Number: L00197077
### Module: Software Development Project

### Scrum Board: https://l00197077.atlassian.net/jira/software/projects/PGSA/boards/34?filter=&groupBy=none

# Password Generator & Strength Analyser

A Java application and web service providing secure password generation and strength analysation.

## Features
- **Password Generation:** Configurable character length (8–128) and character set rules.
- **Entropy Calculation:** Password Strength Analyser
- **RESTful API:** `POST /api/generate` handling requests and validation.
- **Web UI:** Interactive single-page interface hosted via Javalin.

## Requirements/Tools Used
* Java 17 LTS
* Apache Maven 3.x
* JUnit Testing (TBD)
* JaCoCo (TBD)

## Build & Test Instructions
To execute full build verification and JaCoCo coverage reports:

## Build & Test Instructions

### Option 1: Via IDE (IntelliJ IDEA)
If Maven is not installed globally on your machine, use IntelliJ's built-in Maven tool window:

1. Open the project in IntelliJ IDEA.
2. Open the **Maven Tool Window** on the right sidebar (`View` -> `Tool Windows` -> `Maven`).
3. Expand **Lifecycle** under the project tree:
    - Double-click **`clean`**, then double-click **`test`** to run the unit and integration test suite.
4. Expand **Plugins** -> **`jacoco`**:
    - Double-click **`jacoco:report`** to generate the code coverage report in `target/site/jacoco/index.html`.
5. To run the application server:
    - Expand **Plugins** -> **`exec`** -> **`exec:java`**, or run `App.java` directly.

### Option 2: Via Terminal / PowerShell
Run commands from the project root using the included Maven Wrapper script:

```powershell
# Run full unit & integration test suite
.\mvnw.cmd clean test

# Generate JaCoCo code coverage report
.\mvnw.cmd jacoco:report

# Launch Javalin server (port 7070)
.\mvnw.cmd exec:java -Dexec.mainClass="com.passwordutil.App"