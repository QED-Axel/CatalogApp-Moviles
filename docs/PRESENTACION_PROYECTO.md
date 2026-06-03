# Documento de Presentación: CatalogApp

Este documento sirve como referencia detallada sobre la construcción, arquitectura y tecnologías utilizadas en el desarrollo de **CatalogApp**, un proyecto Full-Stack compuesto por una aplicación móvil Android y un backend en la nube.

---

## 1. Visión General del Proyecto
**CatalogApp** es una plataforma móvil para explorar y descubrir películas. Se construyó dividiendo el sistema en dos componentes principales fuertemente tipados:
- **Frontend (Mobile):** Una aplicación Android nativa construida 100% con Kotlin.
- **Backend (API REST):** Un servidor construido con NestJS (TypeScript) que sirve como puente de datos y almacena información en una base de datos propia.

---

## 2. Arquitectura y Tecnologías del Frontend (Android)
La aplicación móvil se diseñó siguiendo los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)**, garantizando que el código sea escalable, testeable y fácil de mantener.

* **Kotlin:** Lenguaje de programación principal, moderno y seguro.
* **Jetpack Compose (Material Design 3):** Sustituyendo los antiguos archivos XML, toda la interfaz de usuario se construyó de manera declarativa.
* **Retrofit & OkHttp:** Utilizados para el consumo seguro de la API. Se implementaron Interceptores de OkHttp para inyectar cabeceras dinámicas.
* **Corrutinas & StateFlow:** Manejo de asincronía y reactividad de estado para una comunicación fluida entre el ViewModel y la UI.
* **Coil:** Librería ligera para la carga eficiente y asíncrona de imágenes (los pósters de las películas) desde la red.
* **Navigation Compose:** Para el enrutamiento y la navegación nativa entre pantallas (Onboarding, Home, Detail).

---

## 3. Arquitectura y Tecnologías del Backend (NestJS)
Para proteger la integridad de las llaves de API externas (TheMovieDB) y tener un mayor control de los datos, se desarrolló un servidor intermediario.

* **Node.js & NestJS:** Framework backend que promueve una arquitectura modular orientada a controladores y servicios.
* **Prisma ORM:** Un mapeador objeto-relacional (ORM) moderno que provee seguridad de tipos.
* **PostgreSQL:** Base de datos relacional para guardar el catálogo local de películas.
* **TheMovieDB (TMDb) API:** Proveedor de datos maestro del cual el backend se alimenta.
* **Docker & Docker Compose:** El backend está completamente contenerizado para asegurar que funcione exactamente igual en desarrollo y en producción.

---

## 4. Características Estrella y Retos Técnicos Resueltos

Durante el desarrollo, nos enfocamos fuertemente en la Experiencia de Usuario (UX) y en resolver problemas técnicos complejos:

### A. Estética Moderna y Glassmorphism
Evitamos los colores genéricos. Construimos una paleta de colores oscura (*Dark Mode*) con gradientes verticales vibrantes (`Brush.verticalGradient`). Implementamos un efecto de *Glassmorphism* (cristal esmerilado) en las barras superiores de navegación para dar una sensación premium.

### B. Internacionalización Dinámica (i18n) Invisible
En lugar de forzar un idioma fijo (ej. español), programamos el cliente de Android para detectar el idioma del sistema operativo (`Locale.getDefault()`). Esta información se envía invisiblemente mediante la cabecera HTTP `Accept-Language`. El servidor NestJS intercepta esta cabecera y hace peticiones a TMDb en el idioma del usuario, haciendo la app global sin añadir botones extra.

### C. Desplazamiento Unificado (LazyVerticalGrid) y Pull to Refresh
Para maximizar el uso de pantalla, reestructuramos la `HomeScreen` utilizando `GridItemSpan` en Jetpack Compose. Esto permite que la barra de búsqueda y la sección de tendencias se desplacen fluidamente junto con el catálogo de películas. Además, añadimos un sistema `PullToRefreshBox` que permite recargar la base de datos deslizando hacia abajo.

### D. Algoritmo de Aleatoriedad y Sincronización Masiva
El endpoint de sincronización del servidor (`/catalog/sync-tmdb`) itera asíncronamente sobre las primeras 5 páginas de resultados de TMDb, descargando las 100 películas más populares en PostgreSQL. Por el lado del frontend, el `CatalogViewModel` utiliza un algoritmo de barajado (`shuffled()`) para que los usuarios vean películas diferentes cada vez que abren la aplicación.

---

## 5. Despliegue en Producción (Cloud)
El servidor backend no se ejecuta localmente. Está desplegado de forma permanente en una Máquina Virtual (VM) alojada en **Google Cloud Platform (GCP)**, exponiendo el puerto 3000 hacia el exterior (IP pública `35.255.161.52`). La app móvil de Android está compilada para apuntar directamente a esa IP en producción, logrando un entorno 100% funcional y real.
