# EasyPark – Backend

API REST para la gestión de un parqueadero: usuarios y administradores, vehículos, espacios, reservas, tarifas, pagos, facturación, reportes, calificaciones y soporte.

Proyecto académico de la **Corporación Universitaria del Huila (CORHUILA)** – Ingeniería de Sistemas.
Backend desarrollado por **Oscar Guillermo Sierra Lozano**. El frontend (Angular) está en [frontend-parqueadero](https://github.com/Oscarsl10/frontend-parqueadero).

## Tecnologías

- Java 17 · Spring Boot 3.3
- Spring Web · Spring Data JPA (Hibernate)
- MySQL (H2 en memoria para pruebas)
- Spring Mail (recuperación de contraseña y mensajes de soporte)
- Springdoc OpenAPI / Swagger UI
- JUnit 5 · Mockito · MockMvc
- Maven (wrapper incluido)

## Funcionalidades

| Módulo | Qué hace |
|---|---|
| Usuarios y administradores | Registro, inicio de sesión, consulta y actualización por email, recuperación de contraseña por correo |
| Vehículos y tipos de vehículo | CRUD de vehículos y sus categorías |
| Espacios | Control del total de espacios disponibles |
| Registro de vehículos | Entrada y salida de vehículos del parqueadero |
| Reservas | Creación, confirmación y consulta de reservas |
| Tarifas y tipos de tarifa | Configuración de precios |
| Pagos y facturas | Pagos y facturación de estadías y de reservas |
| Reportes y calificaciones | Reportes del servicio y calificaciones de los usuarios |
| Soporte | Envío de solicitudes de soporte por correo |

## Estructura

```
backend_EasyPark/
└── src/main/java/com/corhuila/backend_EasyPark/
    ├── controllers/          # 16 controladores REST bajo /api
    ├── configuration/        # Configuración CORS
    ├── requests/             # DTOs de entrada (login, etc.)
    └── models/
        ├── entity/           # Entidades JPA
        ├── repository/       # Repositorios Spring Data
        └── service/          # Lógica de negocio y envío de correos
```

## Cómo ejecutarlo

**Requisitos:** JDK 17 y MySQL 8.

1. Crea la base de datos `backend_EasyPark` en MySQL.
2. Ajusta `src/main/resources/application.properties` con **tus propios** datos:
   - `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
   - `spring.mail.username` y `spring.mail.password` (cuenta SMTP para el envío de correos)
3. Ejecuta:

```bash
cd backend_EasyPark
./mvnw spring-boot:run
```

La API queda en `http://localhost:8082/api` y la documentación interactiva en
`http://localhost:8082/swagger-ui.html`.

> Nota: `spring.jpa.hibernate.ddl-auto=create-drop` recrea las tablas en cada arranque y `import.sql` carga datos iniciales.

## Pruebas

```bash
cd backend_EasyPark
./mvnw test
```

Incluye pruebas unitarias de entidades y servicios (Mockito) y una prueba de integración del controlador de usuarios con MockMvc sobre H2.

## Ejemplos de endpoints

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/addUser` | Registrar usuario |
| POST | `/api/loginUser` | Iniciar sesión |
| POST | `/api/recuperar-contrasenia` | Recuperar contraseña por correo |
| GET | `/api/vehiculo` | Listar vehículos |
| POST | `/api/reserva` | Crear reserva |
| PUT | `/api/confirmar/{id}` | Confirmar reserva |
| GET | `/api/tarifa` | Listar tarifas |
| POST | `/api/pago` | Registrar pago |
| POST | `/api/soporte/enviar` | Enviar solicitud de soporte |

La lista completa está en Swagger UI.
