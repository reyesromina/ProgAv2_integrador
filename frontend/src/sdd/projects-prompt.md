Prompt Implementación Frontend - Listado de Proyectos

Implementar la funcionalidad "Listado de Proyectos" en Angular siguiendo estrictamente las siguientes reglas.

Arquitectura


Utilizar componentes Standalone.
Utilizar ChangeDetectionStrategy.OnPush.
Utilizar Signals para el estado (lista de proyectos, carga, error).
Utilizar inject() en lugar de constructor para las dependencias.


Navegación


No modificar AppComponent.
La navegación debe realizarse exclusivamente mediante Angular Router (Router.navigate).
No utilizar window.location.
Click en una card: navegar a /proyectos/:id usando el campo idProject.
Botón "+": navegar a /proyectos/nuevo.


API


No hardcodear URLs dentro del componente.
Consumir la API únicamente mediante ProjectService.
La URL base debe obtenerse desde environment.ts (environment.apiUrl).
El endpoint es GET /users/{userId}/projects, donde userId viaja en la URL.
El userId se obtiene de localStorage (clave userId).
No asumir rutas diferentes a las definidas en el SPEC.


Modelo de Datos

Crear una interfaz ProjectResponse que refleje el backend:


idProject: number (OJO: el backend lo serializa como "idProject", no "id")
name: string
description: string
projectStatus: string
user: { id: number; email: string }


Componente

El componente debe:


Al iniciar (ngOnInit), obtener el userId de localStorage.
Si no hay userId, mostrar mensaje de error y no llamar a la API.
Si hay userId, llamar a ProjectService.getProjects(userId).
Guardar el resultado en una signal de proyectos.
Manejar los estados de carga y error con signals.


HTML


Utilizar Bootstrap (grilla row/col, cards).
Mostrar los cuatro estados: cargando (spinner), error (alert), lista vacía (mensaje + botón), y grilla de cards.
Cada card muestra únicamente el nombre del proyecto.
Click en la card invoca la navegación al detalle usando idProject.
Un botón "+" en el encabezado navega a crear proyecto.


Restricciones

NO:


Modificar AppComponent.
Hardcodear URLs ni tokens.
Crear servicios duplicados (reutilizar ProjectService si ya existe).
Usar constructor para inyección (usar inject()).


Resultado Esperado

Generar:


ProjectService (con método getProjects(userId)).
ProjectListComponent.
HTML.
CSS.
Interfaz ProjectResponse.


El código debe compilar sin modificaciones adicionales.

Dependencias (pantallas relacionadas, NO generar aquí)


/proyectos/nuevo: pantalla de creación de proyecto (ya existente).
/proyectos/:id: pantalla de detalle del proyecto, donde se muestran los datos del proyecto y se pueden agregar tareas (se implementa por separado)