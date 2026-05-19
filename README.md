# SpringUMA — Medical Records System

![Build Status](https://github.com/sergioldna/IPS-Practica-Hospital/actions/workflows/ci.yml/badge.svg)
![Cobertura Jacoco](.github/badges/jacoco.svg)
![License](https://img.shields.io/github/license/sergioldna/IPS-Practica-Hospital.svg)

CI/CD workflow: [.github/workflows/ci.yml](.github/workflows/ci.yml)

A Spring Boot REST API that models a small medical records system. Built as a practice project for the **Software Maintenance and Testing** course at the University of Málaga.

---

## Domain Model

| Entity    | Description                                                             |
|-----------|-------------------------------------------------------------------------|
| `Medico`  | Medical doctor with `id`, `nombre`, `dni`, `especialidad`               |
| `Paciente`| Patient with `id`, `nombre`, `dni`, `edad`, `cita`, assigned doctor     |
| `Imagen`  | Medical image (binary, compressed) linked to a patient                  |
| `Informe` | AI-generated prediction report linked to an image                       |

Relationships: `Medico` ←1:N→ `Paciente` ←1:N→ `Imagen` ←1:N→ `Informe`

---

## Project Structure

```
src/
├── main/java/com/uma/example/springuma/
│   ├── controller/
│   │   ├── MedicoController.java
│   │   ├── PacienteController.java
│   │   ├── ImagenController.java
│   │   └── InformeController.java
│   ├── model/               # JPA entities, services and repositories
│   └── utils/
│       └── ImageUtils.java  # Compress / decompress helpers
└── test/java/com/uma/example/springuma/
    └── integration/
        ├── base/AbstractIntegration.java              # Shared Spring Boot test config
        ├── MedicoControllerMockMvcIT.java             # Doctor CRUD via MockMvc
        ├── PacienteControllerMockMvcIT.java           # Patient CRUD via MockMvc
        ├── ImagenControllerWebTestClientIT.java       # Image upload/download via WebTestClient
        └── InformeControllerWebTestClientIT.java      # Report lifecycle via WebTestClient
```

---

Jacoco HTML report is generated at `target/site/jacoco/index.html` after running `./mvnw verify`.


## Requirements

| Tool  | Min version |
|-------|-------------|
| Java  | 21          |
| Maven | 3.8+        |

No external database required — H2 in-memory is used for tests.

**Quick start**

- Build and run with the included Maven wrapper (no system Maven required):

```bash
./mvnw -B package
java -jar target/*-SNAPSHOT.jar
```

- Run the app in development mode:

```bash
./mvnw spring-boot:run
```

- Docker (build + run):

```bash
docker build -t springuma:latest .
docker run --rm -p 8080:8080 springuma:latest
```

- Kubernetes (apply provided manifests):

```bash
kubectl apply -f deployment.yml
kubectl apply -f service.yml
```

---

## Running the application

```bash
./mvnw spring-boot:run
```

API available at `http://localhost:8080`.
Swagger UI (SpringDoc): `http://localhost:8080/swagger-ui/index.html`  
OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Main endpoints

| Method | Path                      | Description                          |
|--------|---------------------------|--------------------------------------|
| GET    | `/medico/{id}`            | Get doctor by ID                     |
| GET    | `/medico/dni/{dni}`       | Get doctor by DNI                    |
| POST   | `/medico`                 | Create doctor                        |
| PUT    | `/medico`                 | Update doctor                        |
| DELETE | `/medico/{id}`            | Delete doctor                        |
| GET    | `/paciente/{id}`          | Get patient by ID                    |
| GET    | `/paciente/medico/{id}`   | List patients by doctor              |
| POST   | `/paciente`               | Create patient                       |
| PUT    | `/paciente`               | Update patient                       |
| DELETE | `/paciente/{id}`          | Delete patient                       |
| POST   | `/imagen`                 | Upload image (multipart)             |
| GET    | `/imagen/{id}`            | Download image bytes                 |
| GET    | `/imagen/info/{id}`       | Get image metadata                   |
| GET    | `/imagen/predict/{id}`    | Get AI prediction for image          |
| GET    | `/imagen/paciente/{id}`   | List images for a patient            |
| DELETE | `/imagen/{id}`            | Delete image                         |
| GET    | `/informe/{id}`           | Get report by ID                     |
| GET    | `/informe/imagen/{id}`    | List reports for an image            |
| POST   | `/informe`                | Create report                        |
| DELETE | `/informe/{id}`           | Delete report                        |

---

## Running tests

```bash
# Unit tests only
./mvnw test

# All tests (unit + integration)
./mvnw verify
```

---

## CI/CD — GitHub Actions

The workflow in `.github/workflows/ci.yml` triggers on every **push** and **pull request** and:

1. Compiles the project with JDK 21.
2. Runs unit tests (`maven-surefire-plugin`).
3. Runs integration tests (`maven-failsafe-plugin`, classes ending in `IT`).
4. Publishes a test report to the GitHub **Checks** tab via `dorny/test-reporter`.
5. Uploads XML reports as a downloadable artifact (retained 7 days).

---

## Technology Stack

- Java 21 · Spring Boot 3.2
- Spring Data JPA + H2
- JUnit 5 · MockMvc · WebTestClient
- SpringDoc OpenAPI (Swagger UI)
- Maven · GitHub Actions

---

## Troubleshooting

- **JaCoCo instrumentation errors / Unsupported class file major version:** al ejecutar tests localmente puede aparecer una excepción de instrumentación de JaCoCo (incompatibilidad con la versión del JDK). Opciones:
    - **Actualizar JaCoCo** en `pom.xml` a una versión compatible con el JDK que usas (recomendado para CI y local).
    - **Ejecutar tests con un JDK compatible** (configurar Maven Toolchains o usar la versión objetivo del proyecto).
    - **Solución temporal local:** omitir la instrumentación de JaCoCo al ejecutar tests localmente:

```bash
mvn test -Djacoco.skip=true
```

    Esto evitará el agente de cobertura localmente mientras se actualiza la herramienta.

---

## Contributing (short)

- Create a topic branch named `docs/...` or `feat/...` according to the change.
- Run the test suite before committing:

```bash
./mvnw test
```

- Commit messages follow Conventional Commits. Example:

```bash
git commit -m "docs: update README with troubleshooting and badges"
```

- See also `AGENTS.md` and `GIT_GUIDELINES.md` for more context.
