# CatalogApp - Proyecto Móvil y Backend

Este repositorio contiene el código fuente para el proyecto de Desarrollo de Aplicaciones Móviles.

## Estructura del Proyecto

- **`mobile/`**: Aplicación Android construida con Kotlin, Jetpack Compose y Clean Architecture (MVVM). Consume la API REST mediante Retrofit y guarda datos locales con Room.
- **`backend/`**: API RESTful construida con NestJS y Prisma (TypeScript). Conectada a una base de datos PostgreSQL.

## Instrucciones para el Entorno Local

### 1. Base de Datos (Backend)
Es necesario tener Docker instalado para levantar la base de datos PostgreSQL de desarrollo.
```bash
cd backend
docker compose up -d
npm install
npx prisma migrate dev
npm run start:dev
```

### 2. Aplicación Móvil
Abre la carpeta `mobile/` con **Android Studio** (Koala o superior). El proyecto utilizará Gradle para sincronizar las dependencias.

> **Nota:** La URL base para el consumo de la API se encuentra configurada en el cliente Retrofit (por defecto `10.0.2.2` si usas el emulador de Android).
