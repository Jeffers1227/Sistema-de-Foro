# 📢 Spring Boot Forum System

¡Bienvenido a **SpringForum**! Una plataforma de comunidad completa y robusta desarrollada con **Java** y **Spring Boot**. Este sistema permite a los usuarios crear debates, compartir conocimientos y validar respuestas útiles, todo bajo un entorno seguro y moderno.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Thymeleaf](https://img.shields.io/badge/Frontend-Thymeleaf-lightgrey)

## 🚀 Características Principales

* **Autenticación y Seguridad:** Registro de usuarios, Login y gestión de sesiones con **Spring Security**.
* **Roles de Usuario:**
    * **Usuario:** Puede crear foros, comentar, editar/borrar su propio contenido.
    * **Admin:** Control total para moderar (editar/eliminar) cualquier foro o comentario.
* **Gestión de Contenido (CRUD):** Creación, lectura, actualización y eliminación de foros y comentarios.
* **Editor de Texto Rico:** Integración con **Summernote** para posts con formato (negrita, listas, imágenes).
* **Validación de Respuestas:** El autor de un foro puede marcar un comentario como "Solución Útil" ✅.
* **Perfiles de Usuario:** Avatares generados automáticamente (DiceBear) e historial de publicaciones.
* **Buscador y Paginación:** Búsqueda por palabras clave y navegación optimizada.
* **Contador de Visitas:** Registro de visualizaciones en tiempo real para cada foro 👁️.

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Spring Security.
* **Frontend:** Thymeleaf, Bootstrap 5, Bootstrap Icons.
* **Base de Datos:** MySQL.
* **Herramientas:** Maven, Lombok.

## ⚙️ Instalación y Configuración

Sigue estos pasos para ejecutar el proyecto en tu entorno local.

### 1. Requisitos Previos
* Java JDK 17 o superior.
* Maven.
* MySQL (XAMPP, MySQL Workbench, o Docker).

### 2. Configuración de la Base de Datos (IMPORTANTE)
El proyecto requiere una base de datos MySQL para arrancar.

1.  Abre tu cliente SQL (phpMyAdmin, Workbench, etc.).
2.  Ejecuta el siguiente comando para crear la base de datos:

```sql
CREATE DATABASE forum_db;

y por último en colocar en el terminal:
 mvn spring-boot:run

Una vez que veas el mensaje Started ForumSystemApplication, abre tu navegador en: 👉 http://localhost:8080

Usuarios de PruebaEl sistema incluye un script de inicialización (DataInitializer.java) que crea dos usuarios por defecto para que puedas probarlo

Usuario: admin
Contraseña: 1234

Usuario: pepe
Contraseña: 1234

