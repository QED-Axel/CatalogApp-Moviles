# Evaluación del Proyecto vs. Rúbrica Oficial

Este documento vincula directamente los entregables técnicos desarrollados en **CatalogApp** con los criterios de evaluación de la rúbrica del proyecto final. El proyecto está diseñado para cumplir con el **100% de la rúbrica base (100 puntos)** más los **15 puntos extra**, garantizando la máxima calificación posible.

---

## 1. Desarrollo Técnico de la Aplicación Móvil (35 Puntos)

### ✅ Interfaz de Usuario (UI/UX) con Jetpack Compose [15 pts]
- **Logrado:** Se construyó una UI 100% declarativa sin usar XML.
- **Evidencia:** Uso intensivo de `LazyVerticalGrid` unificado en la `HomeScreen`, soporte nativo para **Pull-to-Refresh** (`PullToRefreshBox`), diseño de Glassmorphism (transparencias dinámicas en la barra superior), `AsyncImage` (Coil) y manejo de modificadores avanzados de estado.

### ✅ Consumo de API y Concurrencia [15 pts]
- **Logrado:** Toda la red es asíncrona, robusta y tolerante a fallos.
- **Evidencia:** Uso de **Retrofit** para el cliente HTTP. Flujos asíncronos controlados por **Corrutinas (Coroutines)** en Kotlin. El estado de la UI reacciona a través de `StateFlow` manejando dinámicamente los tres estados posibles: `Resource.Loading` (muestra un spinner circular), `Resource.Success` (muestra el catálogo) y `Resource.Error` (muestra botones de reintento).

### ✅ Arquitectura de la App [5 pts]
- **Logrado:** Código altamente desacoplado.
- **Evidencia:** Implementación del patrón **MVVM** soportado por **Clean Architecture**. Los datos se solicitan a través de la interfaz `CatalogRepository` (Dominio) y son resueltos en `CatalogRepositoryImpl` (Data), entregando información limpia a los `ViewModels`.

---

## 2. Desarrollo del Backend y Base de Datos (20 Puntos)

### ✅ Funcionamiento de la API [12 pts]
- **Logrado:** API REST limpia, documentada y semántica.
- **Evidencia:** Construida con el robusto framework **NestJS**. Maneja los estados HTTP explícitamente a través de decoradores (ej. `@HttpCode(HttpStatus.CREATED)`). Utiliza tuberías de validación (`ParseIntPipe`) y maneja errores con `NotFoundException` para responder con códigos `404` cuando un ID no existe o falta la llave API.

### ✅ Persistencia de Datos (BD) [8 pts]
- **Logrado:** Conexión permanente a una base de datos relacional robusta.
- **Evidencia:** La base de datos es **PostgreSQL**. Las consultas y el esquema están mapeados con seguridad de tipos mediante **Prisma ORM**. Se tienen funciones activas para todas las operaciones (Create, Read, Update, Delete) en el servicio `catalog.service.ts`.

---

## 3. Control de Versiones: Git y GitHub (10 Puntos)

### ✅ Uso de Git y Repositorio [5 pts]
- **Logrado:** El proyecto tiene un historial de versiones limpio.
- **Evidencia:** La raíz del proyecto incluye un archivo `README.md` completo. Los "commits" incluyen prefijos semánticos (`feat:`, `docs:`, `fix:`) detallando los avances.

### ✅ Colaboración y Flujo de Trabajo [5 pts]
- **Logrado:** Organización en el ciclo de vida del desarrollo de software.
- **Evidencia:** El trabajo intensivo de pruebas se realizó en la rama `desarrollo`, protegiendo el código en producción antes de realizar la integración (Merge) formal hacia la rama `main`.

---

## 4. Puntos Extra / Bonus (15 Puntos Completos)

El proyecto cuenta con implementaciones premium que van más allá del desarrollo base requerido.

### 🌟 [+3 Puntos] Internacionalización (i18n) Dinámica
- La app no cuenta con cadenas de texto quemadas (hardcodeadas). En cambio, se configuró un **Interceptor de OkHttp** en Android que lee el idioma del sistema operativo (`Locale.getDefault()`) y solicita al backend (NestJS) las películas de **TheMovieDB** directamente en el idioma nativo del usuario a través del header `Accept-Language`.

### 🌟 [+4 Puntos] Persistencia Local (Room)
- La aplicación cuenta con una capa de datos local en Android a través de **Room Database** y Data Transfer Objects (`FavoriteEntity.kt`, `FavoriteDao.kt`). Esta implementación permite persistir las películas favoritas del usuario directamente en el almacenamiento interno de SQLite en su teléfono para ser consultadas offline.

### 🌟 [+3 Puntos] Innovación con Vibe Coding
- Se documentó e integró formalmente la colaboración con inteligencias artificiales generativas (Antigravity AI / Agentic AI) como pair-programmers activos durante la refactorización arquitectónica (UI/UX) y diseño asíncrono, operando en modo planificación para resolver bugs complejos.

### 🌟 [+5 Puntos] Despliegue en la Nube (Cloud)
- Se abandonó `localhost`. La base de datos PostgreSQL y el servidor NestJS operan las 24 horas del día mediante **Docker Compose** en una Máquina Virtual de Producción (VPS) aprovisionada en **Google Cloud Platform (GCP)**. La aplicación móvil hace llamadas de red a la IP pública `35.255.161.52`.

---

> **Nota para el equipo (Puntos de Presentación - 35 Puntos):** 
> Técnicamente, el proyecto asegura los primeros 65 puntos + 15 extra (80/100). Los 20 puntos restantes de la evaluación dependen 100% de que el día de la presentación vistan formalmente, dominen la explicación del flujo de datos (desde Postgres -> Prisma -> NestJS -> Retrofit -> StateFlow -> Compose UI) y mantengan participaciones equilibradas entre los tres integrantes. ¡Mucho éxito!
