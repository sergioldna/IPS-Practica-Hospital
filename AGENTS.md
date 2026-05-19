# AGENTS for IPS-Practica-Hotel-IA

Este documento describe agentes recomendados y cómo interactuar con este proyecto.

**Resumen**
- **Propósito:** Aplicación Java Spring Boot (Maven) con contenedorización y manifiestos de despliegue.
- **Lenguaje:** Java (Maven). **Build:** `./mvnw` / `mvn`.

**Archivos clave**
- **Proyecto Maven:** [pom.xml](pom.xml)
- **Código fuente:** [src/main/java](src/main/java)
- **Recursos:** [src/main/resources/application.properties](src/main/resources/application.properties)
- **Docker:** [Dockerfile](Dockerfile)
- **Kubernetes / despliegue:** [deployment.yml](deployment.yml), [service.yml](service.yml), [runner-permissions.yml](runner-permissions.yml)

**Comandos útiles**
- **Compilar y empaquetar:** `./mvnw clean package`
- **Ejecutar localmente:** `./mvnw spring-boot:run`
- **Tests unitarios:** `./mvnw test`
- **Tests de integración / verify:** `./mvnw verify`
- **Construir Docker:** `docker build -t <image>:<tag> . -f Dockerfile`
- **Aplicar manifiestos K8s:** `kubectl apply -f deployment.yml`

**Agentes recomendados**
- **Explore**: lectura y mapeo del repo para responder preguntas de arquitectura. Trigger: manual. Acciones: `read_file`, `file_search`.
- **BuildAndTest**: ejecuta `./mvnw clean package`, recoge fallos de compilación y test, y propone correcciones. Trigger: PR o manual. Acciones: ejecutar pruebas, analizar reports (`target/surefire-reports`, `target/failsafe-reports`).
- **DependencyBump**: detecta dependencias desactualizadas en `pom.xml`, propone cambios y crea PRs de actualización. Trigger: programado o a demanda. Acciones: editar `pom.xml`, ejecutar `./mvnw -U versions:display-dependency-updates`.
- **DockerizeAndPublish**: construye la imagen desde `Dockerfile`, etiqueta y sube a registry. Trigger: en release o PR. Acciones: `docker build`, `docker push`.
- **K8sDeploy**: valida manifests YAML y despliega en un cluster de prueba. Trigger: PR merge o manual. Acciones: validar `kubectl apply --dry-run=client`, `kubectl rollout status`.
- **CIFixAssistant**: revisa workflows, sugiere mejoras en `deployment.yml` (si es CI) y en permisos de runner. Trigger: PR en `.github/workflows` o manual.
- **DocsUpdater**: mantiene `README.md`, `CONTRIBUTING.md` y agrega secciones sobre cómo ejecutar la aplicación localmente y en Docker.

**Ejemplos de prompts para agentes**
- "Build the project, run tests, and summarize failures with stack traces and affected classes."
- "Check for outdated Maven dependencies and propose a minimal set of version bumps with rationale."
- "Build Docker image, run container locally and confirm the app responds on the configured port."

**Convenciones y alcance**
- **No tocar:** cambios de diseño sin pedir PR de revisión; secrets y credenciales no deben añadirse en texto plano.
- **Formato de commits:** seguir convenciones del repositorio (si existen).
- **Acceso a infra:** los agentes que despliegan necesitan credenciales externas; pedir aprobación manual antes de ejecutar pushes o deploys.

Si quieres, creo una rama/PR con este fichero y ejecuto el agente `BuildAndTest` para validar la compilación aquí mismo.
