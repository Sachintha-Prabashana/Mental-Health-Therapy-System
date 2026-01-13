# ZenithMind

A desktop application built with JavaFX for managing a mental health/therapy center. It includes user authentication, patient management, therapist management, therapy programs and sessions, payments, medical records, and reporting (JasperReports).

## Features
- Login and Sign Up screens
- Role-based dashboards (Admin, Receptionist)
- Manage Patients, Therapists, Therapy Programs, and Therapy Sessions
- Assign programs to patients and track session history
- Payment management and invoices
- Medical record tracking
- Reports with JasperReports (e.g., Patient Therapy History)

## Tech Stack
- Java 21
- JavaFX 21 (controls, FXML) + JFoenix
- Hibernate ORM 6, Jakarta Persistence (JPA)
- MySQL Connector/J
- BCrypt (jbcrypt) for password hashing
- JasperReports for reporting
- Maven for build and dependency management
- Lombok

## Get the code
Clone the repository and enter the project directory:

```bash
# Using SSH (recommended)
git clone git@github.com:<your-org-or-user>/ZenithMind.git
cd ZenithMind

# Or using HTTPS
git clone https://github.com/<your-org-or-user>/ZenithMind.git
cd ZenithMind
```

If you downloaded a ZIP, extract it and open the folder in your IDE.

## Project Structure (high level)
- `src/main/java`
  - `module-info.java` Java module declarations
  - `lk/ijse/...` application code (controllers, entities, config, etc.)
- `src/main/resources`
  - `hibernate.cfg.xml`, `hibernate.properties` ORM configuration
  - `view/*.fxml` JavaFX views (e.g., `LoginPage.fxml`, `AdminDashboard.fxml`, etc.)
  - `css/*`, `icons/*`, `images/*` assets
  - `reports/*.jrxml` JasperReports templates
- `pom.xml` Maven configuration
- `target/` build outputs

## Prerequisites
- Java 21 (JDK)
- Maven 3.8+
- MySQL server

## Database Configuration
Set your database connection in `src/main/resources/hibernate.properties` (or `hibernate.cfg.xml`). Typical properties:

```
hibernate.connection.url=jdbc:mysql://localhost:3306/zenithmind?useSSL=false&serverTimezone=UTC
hibernate.connection.username=your_user
hibernate.connection.password=your_password
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.hbm2ddl.auto=update
```

Ensure the database exists:

```sql
CREATE DATABASE zenithmind;
```

## Build

```bash
mvn clean package
```

This produces `target/ZenithMind-1.0-SNAPSHOT.jar`.

## Run
Running JavaFX modular apps can vary by setup. Try these options:

- Run with Maven:

```bash
mvn javafx:run
```

- Or run the built JAR (if all modules are packaged or available on the module path):

```bash
java -jar target/ZenithMind-1.0-SNAPSHOT.jar
```

If the app uses modules that require the JavaFX runtime, you may need to provide the JavaFX SDK. Example (adapt paths as needed):

```bash
java \
  --module-path "C:\\path\\to\\javafx-sdk-21\\lib" \
  --add-modules javafx.controls,javafx.fxml \
  -jar target/ZenithMind-1.0-SNAPSHOT.jar
```

## Java Module Notes
`module-info.java` declares:
- requires: `javafx.controls`, `javafx.fxml`, `com.jfoenix`, `jakarta.persistence`, `org.hibernate.orm.core`, `jbcrypt`, `jasperreports`, etc.
- opens/exports packages for JavaFX FXML and Hibernate entity reflection.

If you run into module access issues, verify `opens` and `exports` match your package names.

## Reports
JasperReports templates live in `src/main/resources/reports/`. Ensure any report compilation or resource loading paths are correct when packaging.

## Assets
Icons, images, and styles are under `src/main/resources/{icons,images,css}`. Refer to them via classpath resource loading from controllers/FXML.

## Troubleshooting
- JavaFX not found at runtime: Provide `--module-path` to the JavaFX SDK and `--add-modules` as shown above.
- MySQL connection errors: Check `hibernate.properties` credentials and that the DB is reachable.
- Class/module access errors: Ensure your run configuration includes modules declared in `module-info.java`.
- Lombok annotations missing at compile time: Enable annotation processing in your IDE.
- JasperReports missing fonts/resources: Package required fonts/resources or adjust report template paths.

## Development Tips
- Use `mvn -v` to confirm Maven uses the intended JDK.
- In IDEs (IntelliJ/VS Code), configure JavaFX SDK in project settings if not using the Maven JavaFX plugin.
- Keep sensitive credentials out of VCS; use environment-specific configs.

## License
This repository does not declare a license file. If you intend to open-source, add a suitable LICENSE and update this README accordingly.
