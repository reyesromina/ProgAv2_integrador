# Spec Feature: Autenticación de Usuario (Login)

## 1. Descripción General
Permitir a los usuarios registrados autenticarse en la aplicación mediante sus credenciales (email y contraseña). Una vez validado por el backend, el sistema deberá almacenar los tokens de acceso de forma segura en el cliente y redirigir al usuario a la vista principal del sistema.

## 2. Endpoints Involucrados
* **Autenticar Usuario:** `POST http://localhost:8080/api/users/login`
  * **Payload (Input):**
    ```json
    {
      "email": "usuario@ejemplo.com",
      "password": "password123"
    }
    ```
  * **Response Exitoso (200 OK):**
    ```json
    {
      "accessToken": "eyJhbGciOi...",
      "refreshToken": "eyJhbGciOi..."
    }
    ```
  * **Códigos de Error:**
    * `401 Unauthorized`: Credenciales incorrectas (email o contraseña erróneos).

## 3. Restricciones de Negocio y UI
* **Campos obligatorios:** Email y Contraseña.
* **Validación de Email:** Debe cumplir con la estructura estándar de correo electrónico.
* **Manejo de Tokens:** Al recibir la respuesta 200, guardar el `accessToken` en el `localStorage`.
    Guardar refreshToken en `localStorage`.
* **Redirección:** Una vez guardado el token, redirigir automáticamente a la ruta `/proyectos`.

## 4. Lineamientos Técnicos Esenciales (Angular)
* **Componente:** Standalone `LoginComponent` con `ChangeDetectionStrategy.OnPush`.
* **Formularios:** `ReactiveFormsModule` usando `FormBuilder`.
* **Optimización de UI:** No invocar funciones directas en el template HTML para revisar errores. Usar `Signals` o propiedades booleanas computadas para controlar la validez de los campos cuando fueron tocados (`invalid && touched`).
* **Feedback:** Mostrar alertas usando el `ToastService` existente tanto para errores de autenticación como para éxito.

## 5. Criterios de Aceptación (Gherkin)
* **Escenario 1: Inicio de sesión exitoso**
  * **Dado que** el usuario ingresa sus credenciales válidas en el formulario.
  * **Cuando** hace clic en "Ingresar".
  * **Entonces** el servicio procesa el POST, guarda el token en el almacenamiento y el Router lo redirige a `/proyectos`.
* **Escenario 2: Credenciales incorrectas**
  * **Dado que** el usuario ingresa un correo o contraseña que no coinciden en el sistema.
  * **Cuando** envía el formulario.
  * **Entonces** el backend devuelve un 401 y el frontend muestra un Toast de peligro con el mensaje "Credenciales incorrectas".