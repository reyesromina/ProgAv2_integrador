**Prompt Implementación Frontend - Crear Tarea**
Implementar la funcionalidad "Crear Tarea" en Angular siguiendo estrictamente las siguientes reglas.

**Arquitectura**

Utilizar componentes Standalone.
Utilizar ChangeDetectionStrategy.OnPush.
Utilizar ReactiveFormsModule.
Utilizar FormBuilder para la construcción del formulario.
Utilizar Signals cuando sea necesario para estados de carga.

**Navegación**

No modificar AppComponent.
No insertar componentes manualmente dentro de otros componentes.
La navegación debe realizarse exclusivamente mediante Angular Router.
No utilizar window.location.
No utilizar etiquetas HTML con href para navegación interna.
Utilizar routerLink o Router.navigate.
El projectId debe obtenerse del parámetro de ruta mediante ActivatedRoute.

**API**

No hardcodear URLs dentro del componente.
Consumir la API únicamente mediante TaskService.
La URL base debe obtenerse desde environment.ts (environment.apiUrl).
El endpoint es POST /users/{projectId}/task, donde projectId viaja en la URL.
El payload debe incluir status: "IN_PROGRESS" fijo al crear, finishedAt: null, y NO debe incluir createdAt (lo asigna el backend).
No asumir rutas diferentes a las definidas en el SPEC.

**Formulario**

Crear un formulario reactivo con:

title
estimateHours

Validaciones:

title

required
minlength(3)
maxlength(100)


estimateHours

required
min(1)
debe ser numérico (input type="number")


**HTML**


Utilizar clases CSS consistentes con los estilos definidos (se puede utilizar la libreria bootstrap).
Si existe una hoja de estilos asociada, el HTML debe utilizar exactamente las clases esperadas por dicha hoja.
No generar HTML sin clases si existe un CSS preparado para ellas.
Mostrar un mensaje de error específico por cada validación fallida (título obligatorio, horas obligatorias, horas mayores a 0).


**Estado de Carga**

Durante la petición:

Deshabilitar inputs.
Deshabilitar botón.
Mostrar indicador visual de carga.

**Respuestas**

Éxito


Mostrar Toast de éxito.
Limpiar formulario.

**Error**
Mostrar Toast de error.

Restricciones

NO:


Modificar rutas existentes.
Modificar AppComponent.
Agregar lógica de autenticación.
Hardcodear tokens.
Hardcodear URLs.
Crear servicios duplicados.


**Resultado Esperado**

Generar:

TaskService.
CreateTaskComponent.
HTML.
CSS.
Interfaces TypeScript necesarias (TaskRequest, TaskResponse).

El código debe compilar sin modificaciones adicionales.