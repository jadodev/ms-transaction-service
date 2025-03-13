# ms-transaction-service

```textplain

📦 transactions (Microservicio de Transacciones)
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java/com/credibanco/transactions  # Paquete base del microservicio
│   │   │   ├── 📂 application  # Capa de aplicación (Casos de uso y lógica de negocio)
│   │   │   │   ├── 📂 dto  # Data Transfer Objects (DTOs)
│   │   │   │   │   ├── TransactionRequestDto.java   # DTO para solicitudes de transacciones
│   │   │   │   │   ├── TransactionResponseDto.java  # DTO para respuestas de transacciones
│   │   │   │   ├── 📂 service  # Servicios de aplicación
│   │   │   │   │   ├── TransactionService.java       # Interfaz del servicio de transacciones
│   │   │   │   │   ├── TransactionServiceImpl.java   # Implementación del servicio
│   │   │   │
│   │   │   ├── 📂 domain  # Capa de dominio (Modelos y lógica de negocio)
│   │   │   │   ├── 📂 model
│   │   │   │   │   ├── Transaction.java         # Entidad de transacción
│   │   │   │   │   ├── TransactionStatus.java   # Enum para el estado de transacciones
│   │   │   │   │   ├── TransactionType.java     # Enum para el tipo de transacción
│   │   │   │   ├── 📂 port  # Definición de puertos de la arquitectura hexagonal
│   │   │   │   │   ├── TransactionRepository.java  # Puerto de salida para la persistencia de transacciones
│   │   │   │
│   │   │   ├── 📂 infrastructure  # Capa de infraestructura (Adaptadores, API, Configuración)
│   │   │   │   ├── 📂 client
│   │   │   │   │   ├── CardServiceClient.java  # Cliente HTTP para interactuar con el servicio de tarjetas
│   │   │   │   ├── 📂 configuration
│   │   │   │   │   ├── TransactionConfiguration.java  # Configuración de la aplicación
│   │   │   │   ├── 📂 controller
│   │   │   │   │   ├── TransactionController.java  # Controlador REST de transacciones
│   │   │   │   ├── 📂 persistence  # Persistencia de datos
│   │   │   │   │   ├── 📂 adapter
│   │   │   │   │   │   ├── TransactionRepositoryAdapter.java  # Adaptador de repositorio para transacciones
│   │   │   │   │   ├── 📂 entity
│   │   │   │   │   │   ├── TransactionEntity.java  # Entidad de base de datos
│   │   │   │   │   ├── 📂 repository
│   │   │   │   │   │   ├── TransactionJpaRepository.java  # Repositorio JPA para transacciones
│   │   │   │
│   │   │   ├── TransactionsApplication.java  # Clase principal de Spring Boot
│   │   │
│   │   ├── 📂 resources  # Archivos de configuración
│   │   │   ├── application-cloud.properties  # Configuración para despliegue en la nube
│   │   │   ├── application.properties        # Configuración local
│   │
│   ├── 📂 test/java/com/credibanco/transactions  # Pruebas unitarias y de integración
│   │   ├── 📂 application/service
│   │   │   ├── TransactionServiceImplTest.java  # Pruebas del servicio de transacciones
│   │   ├── 📂 infrastructure
│   │   │   ├── 📂 adapter
│   │   │   │   ├── TransactionRepositoryAdapterTest.java  # Pruebas del adaptador de persistencia
│   │   │   ├── 📂 configuration
│   │   │   │   ├── TransactionConfigurationTest.java  # Pruebas de configuración
│   │   │   ├── 📂 controller
│   │   │   │   ├── TransactionControllerTest.java  # Pruebas del controlador
│   │   │   ├── 📂 repository
│   │   │   │   ├── TransactionJpaRepositoryTest.java  # Pruebas del repositorio JPA
│   │   ├── TransactionsApplicationTests.java  # Pruebas de integración
│   │
│   ├── 📂 resources
│   │   ├── application-test.properties  # Configuración para pruebas
│
├── .gitattributes         # Configuración de Git
├── .gitignore             # Archivos ignorados por Git
├── Dockerfile             # Archivo para construir la imagen Docker
├── README.md              # Documentación del proyecto


```

# Microservicio de Backend: Gestión de transacciones.

Este proyecto es un microservicio desarrollado en **Java** y **SpringBoot** que permite la creación y consulta de usuarios. Está diseñado para ser **altamente escalable**, forma parte de una **arquitectura de microservicios** y sigue una **arquitectura hexagonal** para garantizar la separación de responsabilidades, la facilidad de mantenimiento y la escalabilidad.

## Características Principales

- Se encaraga de la gestion de las transacciones, tales como listar las transacciones realizadas, debitar dinero, manejar la recarga de dinero y cancelar una transaccion.
- Base de datos MySQL para almacenamiento de datos.
- **Arquitectura hexagonal** para una clara separación entre la lógica de negocio y las capas de infraestructura.
- Despliegue automatizado con **GitHub Actions**, **Docker** y **AWS**.
- Configuración flexible mediante variables de entorno o archivo `application.properties`.

## Estructura del Proyecto

El proyecto está estructurado siguiendo los principios de la **arquitectura hexagonal**, lo que permite una clara separación entre:

- **Capa de Dominio:** Contiene la lógica de negocio y las entidades principales.
- **Capa de Aplicación:** Gestiona los casos de uso y la orquestación de operaciones.
- **Capa de Infraestructura:** Se encarga de la interacción con bases de datos, APIs externas y otros servicios.

Esta estructura facilita la escalabilidad, el mantenimiento y la prueba del microservicio.

## Despliegue Local con Docker

Para ejecutar el proyecto localmente, puedes utilizar Docker. A continuación, se detallan los pasos necesarios para desplegar el microservicio en tu entorno local.

### Requisitos Previos

- **Docker** instalado en tu máquina. Puedes descargarlo desde [aquí](https://www.docker.com/get-started).

### Pasos para Desplegar el Proyecto

1. **Clona el repositorio** en tu máquina local:

   ```bash
   git clone https://github.com/jadodev/ms-transaction-service
   cd ms-transaction-service
  ``
2. **Configura el archivo application.properties:

   ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/transaccions_service?createDatabaseIfNotExist=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

    server.port=8083
  ```
3. **Construye la imagen de Docker para el micros:

  ```bash
    docker build -t ms-transaction-service .
  ```

4. **Levanta una instancia de MySQL con Docker:
  ```bash
    docker run -d --name mysql-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=transactions_service -p 3306:3306 mysql:8.0
  ```

5. **Ejecuta el contenedor para iniciar el microservicio:

  ```bash
    docker run -p 8083:8083 --env-file .env ms-transaction-service
  ```

### Una vez que el contenedor esté en ejecución, puedes acceder al microservicio en:

**http://localhost:8083
