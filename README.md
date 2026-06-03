# CatalogApp - Proyecto Móvil y Backend Full-Stack

Este repositorio contiene el código fuente para el proyecto de Desarrollo de Aplicaciones Móviles. Consiste en una aplicación Android moderna y un servidor API RESTful construido con NestJS.

## 📱 Aplicación Móvil (Android)
Construida con las tecnologías más modernas de desarrollo nativo de Android, enfocada en una experiencia de usuario (UX) inmersiva y dinámica.

### Tecnologías Frontend
- **Kotlin & Jetpack Compose (Material Design 3):** UI declarativa para vistas fluidas.
- **Clean Architecture & MVVM:** Separación de responsabilidades con ViewModels y Casos de Uso.
- **Retrofit & OkHttp:** Para el consumo de la API REST y manipulación de cabeceras HTTP.
- **Coil:** Carga de imágenes de red optimizada y asíncrona.
- **Navigation Compose:** Manejo de rutas nativo.

### Características Principales (App)
- **Diseño Moderno (Glassmorphism):** Headers transparentes y fondos con gradientes dinámicos para una experiencia inmersiva.
- **Internacionalización Dinámica (i18n):** Un Interceptor de OkHttp lee el idioma del dispositivo y automáticamente solicita toda la información al servidor en el idioma del usuario.
- **Pull to Refresh:** Un simple gesto hacia abajo en el inicio sincroniza la app con los últimos datos del servidor.
- **Cuadrícula Unificada (LazyVerticalGrid):** Todo el contenido de la pantalla principal (buscador, tendencias y catálogo) se desplaza orgánicamente, aprovechando todo el tamaño de pantalla.
- **Barajado de Películas (Shuffle):** El catálogo se muestra en orden aleatorio al cargar, invitando al descubrimiento de nuevas películas en las primeras posiciones.
- **Búsqueda Integrada:** Buscador en tiempo real que consulta la base de datos de películas.

## 🌐 Backend (Servidor NestJS)
API RESTful que actúa como puente e intermediario seguro entre la aplicación móvil y bases de datos o servicios de terceros.

### Tecnologías Backend
- **Node.js con NestJS:** Arquitectura modular y escalable.
- **Prisma ORM & PostgreSQL:** Base de datos relacional y tipos de TypeScript seguros.
- **TheMovieDB (TMDb) API:** Proveedor de datos de películas en tiempo real.
- **Docker Compose:** Orquestación de contenedores para despliegue.

### Características Principales (API)
- **Sincronización Masiva (`/catalog/sync-tmdb`):** Endpoint interno para vaciar y refrescar las 100 películas más populares del momento directo desde TMDb a PostgreSQL.
- **Respuestas Multilingües:** La API recibe la cabecera `Accept-Language` del móvil y adapta la solicitud al idioma correcto de TheMovieDB con un fallback seguro a `es-MX`.
- **Rutas de Búsqueda y Tendencias:** Intermediación segura protegiendo las llaves de API y evitando llamadas innecesarias.

## 🚀 Instrucciones para el Entorno Local

### 1. Base de Datos y Backend
Es necesario tener Docker instalado y proveer una llave válida de la API de TMDb.
```bash
cd backend
# Levantar servidor y base de datos
TMDB_API_KEY=tu_api_key_aqui docker compose up -d --build
# Instalar dependencias si desarrollas en local
npm install
npx prisma migrate dev
npm run start:dev
```

### 2. Sincronización Inicial de Películas
Con el servidor corriendo, solicita descargar las 100 películas iniciales:
```bash
curl -X POST http://localhost:3000/catalog/sync-tmdb
```

### 3. Aplicación Móvil
Abre la carpeta `mobile/` con **Android Studio** (Koala o superior).
El proyecto sincronizará sus dependencias con Gradle.
> **Nota de Producción:** La URL base (`BASE_URL`) en `RetrofitClient.kt` apunta a un servidor hospedado en Google Cloud (`35.255.161.52`). Para desarrollo local, utiliza `10.0.2.2` (emulador de Android) apuntando a `localhost`.
