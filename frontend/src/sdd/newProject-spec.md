# Spec Feature: Creación de Proyecto

## 1. Descripción General

Permitir a un usuario autenticado crear un nuevo proyecto dentro del sistema. El proyecto quedará asociado automáticamente al usuario autenticado y podrá utilizarse posteriormente para la gestión de tareas.

---

## 2. Endpoint Involucrado

### Crear Proyecto

**POST** `/users/projects`

#### Payload (Input)

```json
{
  "name": "Sistema de Gestión Académica",
  "description": "Proyecto para administrar recursos académicos",
  "projectStatus": "ACTIVE"
}
```

#### Respuesta Exitosa (200 OK)

```json
{
  "id": 1,
  "name": "Sistema de Gestión Académica",
  "description": "Proyecto para administrar recursos académicos",
  "projectStatus": "ACTIVE"
}
```

#### Códigos de Error

* `400 Bad Request`: Datos inválidos.
* `401 Unauthorized`: Usuario no autenticado.
* `500 Internal Server Error`: Error inesperado.

---

## 3. Restricciones de Negocio

### Nombre

* Obligatorio.
* Longitud mínima: 3 caracteres.
* Longitud máxima: 100 caracteres.

### Descripción

* Obligatoria.
* Longitud máxima: 500 caracteres.

### Estado

* Se enviará como "ACTIVE" por defecto.
* El usuario no podrá modificarlo desde la interfaz.

### Asociación de Usuario

* El usuario autenticado no debe seleccionar manualmente el propietario del proyecto.
* La asociación se realizará automáticamente en el backend utilizando la identidad del usuario autenticado.

---

## 4. Restricciones de UI

### Formulario

Debe contener únicamente:

* Nombre del proyecto.
* Descripción.

### Feedback

Mientras se realiza la petición:

* Deshabilitar el botón de creación.

Al finalizar:

* Mostrar mensaje de éxito.
* Limpiar el formulario.

En caso de error:

* Mostrar mensaje informativo utilizando ToastService.

---

## 5. Criterios de Aceptación

### Escenario 1: Creación Exitosa

Dado que el usuario está autenticado

Cuando completa el formulario con datos válidos

Entonces el sistema crea el proyecto correctamente

Y muestra una notificación de éxito.

---

### Escenario 2: Nombre Vacío

Dado que el usuario intenta crear un proyecto

Cuando deja el nombre vacío

Entonces el formulario impide el envío

Y muestra un mensaje indicando que el nombre es obligatorio.

---

### Escenario 3: Error del Servidor

Dado que el usuario completa correctamente el formulario

Cuando ocurre un error durante la creación

Entonces se muestra un mensaje de error

Y el usuario permanece en la pantalla actual.
