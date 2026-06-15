Spec Feature: Listado de Proyectos

1. Descripción General

Permitir a un usuario autenticado visualizar todos los proyectos que le pertenecen, mostrados como tarjetas (cards) con el nombre del proyecto. Desde esta pantalla el usuario puede:


Hacer click en una card para navegar al detalle del proyecto (donde verá sus datos y podrá agregar tareas).
Usar un botón "+" para navegar a la pantalla de creación de proyecto.


Esta es la pantalla principal posterior al login.


2. Endpoint Involucrado

Listar Proyectos del Usuario

GET /users/{userId}/projects


Path confirmado: @RequestMapping("/users") a nivel de clase + @GetMapping("/{userId}/projects") a nivel de método.



Path Parameter


userId (Long): identificador del usuario autenticado. Se obtiene del valor guardado en localStorage durante el login.


Respuesta Exitosa (200 OK)

json[
  {
    "idProject": 1,
    "name": "Sistema de Gestión Académica",
    "description": "Administrar recursos académicos",
    "projectStatus": "ACTIVE",
    "user": { "id": 12, "email": "usuario@gmail.com" }
  }
]


Importante: el id del proyecto se serializa como idProject (por @JsonProperty("idProject") en el backend), NO como id. La interfaz del frontend debe usar idProject.



Códigos de Error


400 Bad Request: Petición inválida.
401 Unauthorized: Usuario no autenticado.
500 Internal Server Error: Error inesperado.



3. Restricciones de Negocio

Identidad del Usuario


El userId se obtiene de localStorage (clave userId), guardado durante el login.
Si no existe un userId almacenado, no se realiza la petición y se muestra un mensaje de error.


Proyectos Mostrados


Solo se muestran los proyectos del usuario autenticado.
De cada proyecto, en la lista, solo se muestra el nombre.



Nota de seguridad (deuda técnica consciente): el userId viaja en la URL y se toma de localStorage, lo que permitiría que un usuario solicite proyectos de otro alterando el valor. Se elige esta vía por simplicidad en esta etapa. Alternativa a futuro: que el backend derive la identidad del token (claim sub del JWT) en lugar de confiar en el id enviado por el cliente.




4. Restricciones de UI

Estados de la Pantalla

La pantalla debe contemplar cuatro estados:


Cargando: mostrar un indicador mientras se realiza la petición.
Con proyectos: mostrar una grilla de cards, cada una con el nombre del proyecto.
Lista vacía: mostrar un mensaje indicando que no hay proyectos y un botón para crear el primero.
Error: mostrar un mensaje informativo si la petición falla.


Interacciones


Click en una card: navegar al detalle del proyecto (/proyectos/:id), usando el idProject.
Botón "+": navegar a la pantalla de creación de proyecto (/proyectos/nuevo).


Estilo


Utilizar Bootstrap para la grilla y las cards, consistente con el resto del proyecto.



5. Criterios de Aceptación

Escenario 1: Visualización Exitosa

Dado que el usuario está autenticado y tiene proyectos

Cuando accede a la pantalla de proyectos

Entonces se muestra una card por cada proyecto con su nombre.


Escenario 2: Lista Vacía

Dado que el usuario está autenticado y no tiene proyectos

Cuando accede a la pantalla de proyectos

Entonces se muestra un mensaje indicando que no tiene proyectos

Y un botón para crear el primero.


Escenario 3: Navegación al Detalle

Dado que el usuario visualiza la lista de proyectos

Cuando hace click en una card

Entonces el sistema navega al detalle de ese proyecto.


Escenario 4: Navegación a Crear Proyecto

Dado que el usuario visualiza la lista de proyectos

Cuando hace click en el botón "+"

Entonces el sistema navega a la pantalla de creación de proyecto.


Escenario 5: Error al Cargar

Dado que el usuario accede a la pantalla de proyectos

Cuando ocurre un error durante la petición

Entonces se muestra un mensaje de error

Y no se muestran cards.


Escenario 6: Usuario No Identificado

Dado que no existe un userId almacenado en localStorage

Cuando el usuario accede a la pantalla de proyectos

Entonces no se realiza la petición

Y se muestra un mensaje indicando que no se pudo identificar al usuario.