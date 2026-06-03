# Contexto de Continuidad para el Agente (Antigravity AI)

**Para el Agente Antigravity que lea esto:**
Saludos, colega. Si estás leyendo esto, es porque has sido instanciado en una computadora nueva para continuar el desarrollo de este proyecto junto a nuestro usuario, Axel.

Este archivo contiene el estado actual del proyecto, las decisiones arquitectónicas que hemos tomado y el contexto crítico que necesitas para poder operar sin fricciones. No debes modificar este archivo a menos que cambies el stack fundamental.

## Estado del Proyecto (Rama `main`)
El proyecto consta de dos partes interconectadas, construidas de cero con arquitecturas modernas.

### 1. El Backend (NestJS + Prisma + PostgreSQL)
- **Directorio:** `/backend`
- **Orquestación:** Docker Compose (`docker-compose.yml` levanta tanto la DB como la API).
- **Despliegue Actual:** Producción. Está corriendo permanentemente en una VM remota en GCP en la IP pública `35.255.161.52`.
- **Datos (TMDb):** Se alimenta de TheMovieDB. Utiliza una variable de entorno mandatoria `TMDB_API_KEY`.
- **Sincronización:** El endpoint `POST /catalog/sync-tmdb` descarga las primeras 5 páginas (100 películas) populares de TMDb en la base de datos local (PostgreSQL).
- **Idiomas (i18n):** El controlador de Catálogo intercepta la cabecera `Accept-Language` enviada por la app para devolver la data de TMDb en el idioma del teléfono del usuario (con `es-MX` de fallback).

### 2. La Aplicación Móvil (Android / Jetpack Compose)
- **Directorio:** `/mobile`
- **Versión Compose:** Dependencias usando BOM de 2026.03.01 (Material Design 3 versión >= 1.3.0, lo que significa que el componente para jalar para recargar es `PullToRefreshBox`, *NO* el obsoleto `PullToRefreshContainer`).
- **Arquitectura:** Clean Architecture, MVVM. Uso de Kotlin Coroutines y StateFlows.
- **Red:** Retrofit con GsonConverterFactory. *IMPORTANTE:* Un OkHttp Interceptor en `RetrofitClient.kt` inyecta dinámicamente `Accept-Language` en todas las peticiones con el Locale del sistema.
- **URL Base:** Apunta a producción por defecto (`http://35.255.161.52:3000/`). Si desarrollas en local con el emulador, tendrás que cambiarlo a `10.0.2.2`.
- **Diseño (UI/UX):** Fuerte enfoque en una estética moderna con "Glassmorphism" (TopAppBar transparente) y un fondo con gradiente vertical global (`MainNavigation.kt`).
- **Navegación:** La UI fue refactorizada para que la HomeScreen entera utilice un único `LazyVerticalGrid`. La barra de búsqueda y las tendencias están inyectadas usando `GridItemSpan(maxLineSpan)` para permitir un scroll unificado que oculta los headers al bajar.
- **Lógica de Catálogo:** Las películas se ordenan de forma aleatoria (`.shuffled()`) en el `CatalogViewModel` cada vez que el estado es cargado (Success).

## Instrucciones para ti (Agente)
- Si necesitas probar cambios del backend, hazlo localmente, pero recuerda que **debes coordinar el despliegue con el usuario** ya que el backend oficial vive en su VM de GCP (`git pull` + `docker compose up -d --build` en su terminal remota).
- Cuando agregues nuevas librerías de Compose, recuerda que estamos en la BOM de 2026. Prioriza APIs modernas.
- ¡Mantén el enfoque en la calidad visual y la experiencia de usuario!

¡Buena suerte, Antigravity! Sigue el gran trabajo.
