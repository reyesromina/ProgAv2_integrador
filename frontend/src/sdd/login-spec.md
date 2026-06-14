# Spec Feature: Autenticación de Usuario (Login)

## 1. Descripción General
Permitir a los usuarios registrados autenticarse en la aplicación mediante sus credenciales (email y contraseña). Una vez validado por el backend, el sistema deberá almacenar los tokens de acceso de forma segura en el cliente y redirigir al usuario a la vista principal del sistema.

## 2. Endpoints Involucrados
* **Autenticar Usuario:** `POST http://localhost:8080/users/login`
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
* **Optimización de UI:** No invocar `this.form.get(...)` directamente en el template. Exponer la validez mediante propiedades `computed()` que reflejen `invalid && touched`. Dado que `FormGroup` (RxJS) no notifica a las Signals automáticamente, sincronizar el estado del formulario con `toSignal()` sobre `valueChanges`/`statusChanges`, y disparar el recálculo de `touched` mediante un evento `(blur)` en los campos.
* **Feedback:** Mostrar alertas usando el `ToastService` tanto para errores de autenticación como para éxito.

## 5. Criterios de Aceptación (Gherkin)
* **Escenario 1: Inicio de sesión exitoso**
  * **Dado que** el usuario ingresa sus credenciales válidas en el formulario.
  * **Cuando** hace clic en "Ingresar".
  * **Entonces** el servicio procesa el POST, guarda el token en el almacenamiento y el Router lo redirige a `/proyectos`.
* **Escenario 2: Credenciales incorrectas**
  * **Dado que** el usuario ingresa un correo o contraseña que no coinciden en el sistema.
  * **Cuando** envía el formulario.
  * **Entonces** el backend devuelve un 401 y el frontend muestra un Toast de peligro con el mensaje "Credenciales incorrectas".


 **Escenarios alternativos : Inicio de sesión**
 # Escenarios que faltaban
**Escenario 3: Email con formato inválido**
  **Dado que** el usuario ingresa "abc" en el campo email
  **Cuando** abandona el campo email
  **Entonces** ve el mensaje "Ingrese un email válido"
  Y el botón de login permanece deshabilitado

**Escenario 4: Email vacío al intentar enviar**
  **Dado que** el usuario deja el campo email vacío
  **Cuando** intenta hacer clic en el botón de login
  **Entonces** ve el mensaje "El email es obligatorio"
 Y el botón de login permanece deshabilitado
**Escenario 5: Contraseña vacía al intentar enviar**
  **Dado que** el usuario deja el campo contraseña vacío
  **Cuando** intenta hacer en el botón de login
  Entonces ve el mensaje "La contraseña es obligatoria"
Y el botón de login permanece deshabilitado
**Escenario 6: Formulario sin errores visibles al cargar**
  **Dado que** el usuario accede a la pantalla de login
  **Cuando** no ha interactuado con ningún campo
  **Entonces** no ve ningún mensaje de error
  