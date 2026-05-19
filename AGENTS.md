# AGENTS

Resumen de agentes recomendados para este proyecto Java Spring (Maven, Docker, Kubernetes).

- **Build & Test Agent**: Ejecuta compilación Maven y pruebas unitarias.
  - Trigger: push/PR en ramas de desarrollo/producción.
  - Permisos: checkout, Java/Maven, almacenamiento de artefactos.
  - Acciones: `./mvnw -B clean verify`, generar informe JaCoCo.
  - Archivos relevantes: [pom.xml](pom.xml), [mvnw](mvnw), [src](src/), [target](target/).

- **Docker Build & Push Agent**: Construye imagen Docker y la publica en registro.
  - Trigger: tag de release o push en rama `main`.
  - Permisos: secretos del registro (`REGISTRY_USER`, `REGISTRY_TOKEN`).
  - Acciones: `docker build -f Dockerfile -t $IMAGE:$TAG .` y `docker push`.
  - Archivos relevantes: [Dockerfile](Dockerfile), artefacto JAR en [target/](target/).

- **Kubernetes Deploy Agent**: Despliega la aplicación en clúster k8s.
  - Trigger: push a `main` con imagen publicada o release.
  - Permisos: `kubeconfig` con acceso a namespace/roles adecuados.
  - Acciones: `kubectl apply -f deployment.yml`, `kubectl apply -f service.yml`.
  - Archivos relevantes: [deployment.yml](deployment.yml), [service.yml](service.yml), [runner-rolebinding.yml](runner-rolebinding.yml), [runner-permissions.yml](runner-permissions.yml).

- **Integration / E2E Test Agent**: Ejecuta pruebas de integración y pruebas de contrato.
  - Trigger: merge en rama de integración o pipeline programado.
  - Permisos: entorno de test, base de datos o mocks.
  - Acciones: `./mvnw -B verify -Pintegration` (o configuración equivalente), recopilar `target/failsafe-reports`.
  - Archivos relevantes: [target/failsafe-reports/](target/failsafe-reports/).

- **Coverage & Report Agent**: Publica informes de cobertura (JaCoCo) y artefactos de site.
  - Trigger: tras ejecutar Build & Test.
  - Acciones: subir `target/site/jacoco/` a almacenamiento o servicio de cobertura.
  - Archivos relevantes: [target/site/jacoco/](target/site/jacoco/).

- **Dependency & Security Scan Agent**: Auditoría de dependencias y escaneo de contenedores.
  - Trigger: push a cualquier rama o programación diaria.
  - Herramientas sugeridas: OWASP Dependency-Check, Snyk, Trivy.
  - Archivos relevantes: [pom.xml](pom.xml), [Dockerfile](Dockerfile).

- **Release Agent**: Bump de versión, tag, build y publicación de release.
  - Trigger: manual (workflow_dispatch) o al crear un release en GitHub.
  - Acciones: actualizar `pom.xml`, crear tag, ejecutar build y publicar artefactos.

- **Docs / Javadoc Agent**: Genera documentación del proyecto y la publica como artifact o Pages.
  - Trigger: push en rama `main` o manual.
  - Acciones: `./mvnw javadoc:javadoc site` y publicar `target/site`.

- **Developer Assistant Agent**: Agente conversacional para ayudar con código, tests y despliegues.
  - Uso: preguntas ad-hoc, sugerencias de refactor, inspección de tests fallidos.
  - Acciones: buscar en `src/`, `test/`, `pom.xml`, y sugerir parches o comandos.

Notas finales:
- Los agentes pueden implementarse como GitHub Actions reutilizables o como flujos independientes.
- ¿Quieres que genere workflows de GitHub Actions para algunos de estos agentes? Indica cuáles.
