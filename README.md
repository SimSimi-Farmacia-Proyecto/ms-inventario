# 💊 Farmacia-SimSimi

Sistema de gestión de farmacia basado en una arquitectura de microservicios desarrollada con Spring Boot.

El proyecto permite administrar clientes, medicamentos, ventas, pagos, recetas, inventario y otros procesos asociados a una farmacia mediante servicios independientes comunicados a través de APIs REST.

---

# 📌 Microservicio: MS Inventario

`msinventario` es el microservicio encargado de administrar el stock disponible de medicamentos.

Sus principales responsabilidades son:

* Registrar inventario de medicamentos.
* Consultar stock disponible.
* Buscar inventario asociado a un medicamento.
* Actualizar cantidades mediante reducción de stock.
* Validar operaciones para evitar cantidades inválidas.
* Exponer una API REST documentada mediante Swagger.

---

# 🏗️ Arquitectura general del sistema

El sistema Farmacia-SimSimi está compuesto por múltiples microservicios independientes.

Cada microservicio posee:

* Su propia lógica de negocio.
* Sus propios controladores.
* Sus servicios.
* Sus repositorios.
* Su propia persistencia.

El microservicio `msinventario` funciona de manera independiente y puede comunicarse con otros servicios del sistema mediante APIs REST.

Arquitectura general:

```
                  Cliente / Frontend
                         |
                         |
                         ▼
                API REST Farmacia
                         |
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼

   mscliente       msmedicamentos     msinventario
      |                 |                 |
      |                 |                 |
      ▼                 ▼                 ▼

  Base datos        Base datos       Base datos
  clientes        medicamentos      inventario
```

---

# ⚙️ Tecnologías utilizadas

| Tecnología         | Uso                            |
| ------------------ | ------------------------------ |
| Java 21            | Lenguaje principal             |
| Spring Boot 3.5.16 | Framework backend              |
| Spring Web         | Desarrollo API REST            |
| Spring Data JPA    | Persistencia de datos          |
| Hibernate          | ORM                            |
| PostgreSQL         | Base de datos                  |
| Lombok             | Reducción de código repetitivo |
| Jakarta Validation | Validación de datos            |
| SpringDoc OpenAPI  | Documentación Swagger          |
| Maven              | Gestión de dependencias        |
| Docker             | Contenedorización              |
| Render             | Despliegue cloud               |

---

# 📂 Estructura del proyecto

```
msinventario

src/main/java/com/farmacia/msinventario

├── config
│
├── controller
│
├── dto
│   ├── request
│   ├── response
│   └── stock
│
├── exception
│
├── mapper
│
├── model
│
├── repository
│
├── service
│   ├── interfaces
│   └── impl
│
└── MsinventarioApplication.java
```

---

# 🔄 Arquitectura interna del microservicio

El flujo interno sigue una arquitectura por capas:

```
          Cliente HTTP
               |
               ▼

     InventarioController

               |
               ▼

       InventarioService

               |
               ▼

     InventarioRepository

               |
               ▼

          PostgreSQL
```

---

# 🗄️ Base de datos

Motor utilizado:

```
PostgreSQL
```

Base de datos:

```
db_ms_inventario
```

Entidad principal:

```
Inventario
```

Campos:

| Campo         | Tipo          | Descripción                   |
| ------------- | ------------- | ----------------------------- |
| id            | Long          | Identificador del inventario  |
| medicamentoId | Long          | Identificador del medicamento |
| cantidad      | Integer       | Stock disponible              |
| actualizadoEn | LocalDateTime | Última actualización          |

La aplicación utiliza:

* Spring Data JPA
* Hibernate
* Relaciones mediante identificadores entre microservicios

---

# 🚀 Ejecución local

## Requisitos previos

Instalar:

* Java 21
* Maven
* PostgreSQL
* Git

También es posible ejecutar mediante Docker.

---

## Clonar repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

Ingresar al proyecto:

```bash
cd msinventario
```

---

## Configurar variables de entorno

Crear:

```
DB_URL
DB_USER
DB_PASSWORD
```

Ejemplo:

```env
DB_URL=jdbc:postgresql://localhost:5432/db_ms_inventario
DB_USER=postgres
DB_PASSWORD=password
```

---

## Ejecutar aplicación

Con Maven:

```bash
./mvnw spring-boot:run
```

La aplicación iniciará en:

```
http://localhost:8086
```

---

# 🐳 Ejecución con Docker

El proyecto utiliza un `Dockerfile` para construir la imagen del microservicio.

Crear imagen:

```bash
docker build -t msinventario .
```

Ejecutar contenedor:

```bash
docker run -p 8086:8086 msinventario
```

La aplicación quedará disponible en:

```
http://localhost:8086
```

---

# 🌎 Despliegue en Render

El microservicio se encuentra desplegado como Web Service en Render.

Flujo de despliegue:

```
GitHub Repository

        |

        ▼

     Dockerfile

        |

        ▼

 Render Web Service

        |

        ▼

 PostgreSQL Render
```

---

## URL producción

Aplicación:

```
https://ms-inventario-4c5o.onrender.com
```

Swagger:

```
https://ms-inventario-4c5o.onrender.com/swagger-ui/index.html
```

OpenAPI:

```
https://ms-inventario-4c5o.onrender.com/v3/api-docs
```

---

# 📖 Documentación Swagger

Swagger permite probar los endpoints directamente desde el navegador.

Disponible:

```
/swagger-ui/index.html
```

Incluye:

* Modelos DTO.
* Métodos HTTP disponibles.
* Ejemplos de solicitudes.
* Respuestas esperadas.

---

# 🔌 Endpoints disponibles

## Obtener inventario completo

```
GET /api/v1/inventarios
```

Respuesta ejemplo:

```json
[
 {
  "id":1,
  "medicamentoId":1,
  "cantidad":100
 }
]
```

---

## Crear inventario

```
POST /api/v1/inventarios
```

Ejemplo:

```json
{
 "medicamentoId":4,
 "cantidad":80
}
```

---

## Buscar por medicamento

```
GET /api/v1/inventarios/medicamento/{medicamentoId}
```

Ejemplo:

```
GET /api/v1/inventarios/medicamento/1
```

---

## Reducir stock

```
POST /api/v1/inventarios/reducciones
```

Ejemplo:

```json
{
 "medicamentoId":1,
 "cantidad":10
}
```

---

# 🧪 Datos iniciales

Al iniciar la aplicación se cargan registros de prueba mediante `CommandLineRunner`.

Datos iniciales:

| ID | Medicamento ID | Cantidad |
| -- | -------------- | -------- |
| 1  | 1              | 100      |
| 2  | 2              | 50       |
| 3  | 3              | 200      |

---

# ⚠️ Manejo de errores

El microservicio implementa:

* `@RestControllerAdvice`
* Excepciones personalizadas.
* Respuestas HTTP controladas.

Excepciones:

```
ResourceNotFoundException
StockInsuficienteException
```

---

# ✅ Validaciones implementadas

Los DTO utilizan Jakarta Validation:

* `@NotNull`
* `@Min`

Esto permite controlar:

* Datos obligatorios.
* Cantidades negativas.
* Solicitudes inválidas.

---

# 📝 Variables de entorno en Render

Configuradas en:

```
Render Dashboard
→ Environment Variables
```

Variables utilizadas:

| Variable    | Descripción           |
| ----------- | --------------------- |
| DB_URL      | URL PostgreSQL        |
| DB_USER     | Usuario PostgreSQL    |
| DB_PASSWORD | Contraseña PostgreSQL |

---

# 👩‍💻 Autor

Proyecto desarrollado como parte de la carrera Ingeniería en Informática.

Microservicio:

```
msinventario
```

Sistema:

```
Farmacia-SimSimi
```
