# Spec

## Feature: Registrar usuario

### Descripción

El usuario ingresará un correo electrónico y una contraseña para registrarse en el sistema.

Al completar el registro exitosamente, el backend creará la cuenta, almacenará la contraseña de forma segura y devolverá un Access Token y un Refresh Token.

El frontend deberá informar al usuario que el registro fue exitoso y permitir posteriormente el acceso a la pantalla de login.

### Endpoint

POST /users

### Request

```json
{
  "email": "string",
  "password": "string"
}
```

### Response

#### 201 Created

```json
{
  "accessToken": "string",
  "refreshToken": "string"
}
```

#### 400 Bad Request

Errores actualmente contemplados por el backend:

* Usuario existente.
* Email inválido.
* Campos incompletos.
* Usuario no pudo ser creado.

Los mensajes específicos serán integrados progresivamente en el frontend.

---

## Consideraciones

* La validación principal de negocio se encuentra implementada en el backend.
* El frontend únicamente deberá realizar validaciones básicas de experiencia de usuario.
* El backend es responsable de:

  * Verificar usuarios existentes.
  * Validar email.
  * Cifrar contraseñas.
  * Generar tokens.
  * Persistir usuarios.

---

## Criterios de aceptación

* Dado que el usuario ingresa un email y contraseña válidos, cuando envíe el formulario de registro, el sistema deberá crear la cuenta y devolver un Access Token y un Refresh Token.

* Dado que el registro fue exitoso, el frontend deberá informar al usuario que la cuenta fue creada correctamente.

* Dado que el registro fue exitoso, el usuario podrá navegar posteriormente a:

```text
/login/user
```

* Dado que ocurre un error durante el registro, el frontend deberá mostrar un mensaje genérico de error.

* Dado que faltan campos obligatorios, el frontend deberá impedir el envío del formulario hasta completar la información requerida.

---

## Fuera de alcance

No implementar todavía:

* Verificación por código enviado por email.
* Activación de cuenta.
* Reenvío de códigos.
* Recuperación de contraseña.
* Manejo detallado de errores de negocio.
