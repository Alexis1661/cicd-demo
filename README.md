# CI/CD Demo Pipeline

## Descripción
Pipeline completo de CI/CD implementado con Jenkins, SonarQube y Trivy.

## Flujo del Pipeline

1. **Checkout** - Obtiene el código fuente desde GitHub
2. **Build** - Compila la aplicación con Maven
3. **Test** - Ejecuta pruebas unitarias (excluye SeleniumExampleTest)
4. **Static Analysis** - Análisis de calidad con SonarQube
5. **Security Scan** - Escaneo de vulnerabilidades con Trivy
6. **Quality Gate** - Valida que el código pasa los estándares
7. **Deploy** - Despliega el contenedor en el ambiente local

## Infraestructura

| Servicio | Puerto | Descripción |
|---|---|---|
| Jenkins | 8080 | Servidor CI/CD |
| SonarQube | 9000 | Análisis de calidad |
| Aplicación | 8081 | App desplegada |

## Cómo ejecutar

```bash
# Levantar Jenkins
docker run -d --name jenkins -p 8080:8080 jenkins/jenkins:lts

# Levantar SonarQube  
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community

# Conectar a la misma red
docker network create ci-network
docker network connect ci-network jenkins
docker network connect ci-network sonarqube
```

## Decisiones técnicas
- SeleniumExampleTest excluido por requerir servidor Selenium externo
- JaCoCo deshabilitado por incompatibilidad con Java 21
- Imagen base actualizada a eclipse-temurin:17 (openjdk:12-alpine descontinuada)