**Spec Feature: Creación de Tarea**

**1. Descripción General**

Permitir a un usuario autenticado crear una nueva tarea dentro de un proyecto existente. La tarea quedará asociada automáticamente al proyecto indicado en la URL y se utilizará para la gestión del trabajo dentro de ese proyecto.


**2. Endpoint Involucrado**

Crear Tarea

POST /users/{projectId}/task


Path confirmado: @RequestMapping("/users") a nivel de clase + @PostMapping("/{projectId}/task") a nivel de método = /users/{projectId}/task. task en singular y projectId viaja en la URL, no en el body.


Path Parameter

projectId (Long): identificador del proyecto al que pertenece la tarea.


**Payload (Input)**

json{
  "title": "Diseñar modelo de datos",
  "estimateHours": 8,
  "status": "IN_PROGRESS",
  "finishedAt": null
}


**Campos confirmados con backend:**


status: lo envía el frontend siempre como "IN_PROGRESS" al crear (el backend no lo asigna).
createdAt: lo asigna el backend al persistir, el frontend NO lo envía.
finishedAt: es null al crear la tarea.
projectId: viaja en la URL como path param, no en el body.




**Respuesta Exitosa (200 OK)**

json{
  "id": 1,
  "title": "Diseñar modelo de datos",
  "estimateHours": 8,
  "status": "IN_PROGRESS",
  "createdAt": "2026-06-14T10:00:00",
  "finishedAt": null,
  "projectId": 1
}




**Códigos de Error**


400 Bad Request: Datos inválidos.
401 Unauthorized: Usuario no autenticado.
500 Internal Server Error: Error inesperado.



**3. Restricciones de Negocio**

Título (title)


Obligatorio.
Longitud mínima: 3 caracteres.
Longitud máxima: 100 caracteres.


Horas estimadas (estimateHours)


Obligatorio.
Debe ser un valor numérico.
Debe ser mayor a 0.


Estado (status)


Se enviará siempre como "IN_PROGRESS" al crear la tarea.
El backend no asigna el estado por defecto: el valor viene desde el frontend.
El usuario no podrá modificarlo desde la interfaz.


Asociación al Proyecto


El usuario no selecciona manualmente el proyecto.
El projectId se obtiene del contexto de navegación (parámetro de ruta) al ingresar desde la pantalla de detalle del proyecto.



**4. Restricciones de UI**

Formulario

Debe contener únicamente:


Título de la tarea.
Horas estimadas.


Feedback

Mientras se realiza la petición:


Deshabilitar el botón de creación.

Al finalizar:

Mostrar mensaje de éxito.
Limpiar el formulario.

En caso de error:
Mostrar mensaje informativo utilizando ToastService.



5. Criterios de Aceptación

**Escenario 1: Creación Exitosa**
**Dado que** el usuario está autenticado y dentro de un proyecto
**Cuando** completa el formulario con título y horas válidas
**Entonces** el sistema crea la tarea correctamente
Y muestra una notificación de éxito.


**Escenario 2: Título Vacío**

**Dado que** el usuario intenta crear una tarea
**Cuando** deja el título vacío y abandona el campo
**Entonces** el formulario impide el envío
Y muestra un mensaje indicando que el título es obligatorio.


**Escenario 3: Horas Vacías o No Numéricas**
**Dado que** el usuario intenta crear una tarea
**Cuando** deja las horas vacías o ingresa un valor no numérico
**Entonces** el formulario impide el envío
Y muestra un mensaje indicando que las horas son obligatorias y deben ser numéricas.


**Escenario 4: Horas con Valor Inválido**
**Dado que** el usuario ingresa un número menor o igual a 0 en horas
**Cuando** abandona el campo
**Entonces** el formulario impide el envío
Y muestra un mensaje indicando que las horas deben ser mayores a 0.


**Escenario 5: Error del Servidor**
**Dado que** el usuario completa correctamente el formulario
**Cuando** ocurre un error durante la creación
**Entonces** se muestra un mensaje de error
Y el usuario permanece en la pantalla actual.