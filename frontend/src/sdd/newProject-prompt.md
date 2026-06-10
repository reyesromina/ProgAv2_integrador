# Prompt Implementación Frontend - Crear Proyecto

Implementar la funcionalidad "Crear Proyecto" en Angular siguiendo estrictamente las siguientes reglas.

## Arquitectura

* Utilizar componentes Standalone.
* Utilizar ChangeDetectionStrategy.OnPush.
* Utilizar ReactiveFormsModule.
* Utilizar FormBuilder para la construcción del formulario.
* Utilizar Signals cuando sea necesario para estados de carga.

## Navegación

* No modificar AppComponent.
* No insertar componentes manualmente dentro de otros componentes.
* La navegación debe realizarse exclusivamente mediante Angular Router.
* No utilizar window.location.
* No utilizar etiquetas HTML con href para navegación interna.
* Utilizar routerLink o Router.navigate.

## API

* No hardcodear URLs dentro del componente.
* Consumir la API únicamente mediante ProjectService.
* La URL base debe obtenerse desde environment.ts.
* No asumir rutas diferentes a las definidas en el SPEC.

## Formulario

Crear un formulario reactivo con:

* name
* description

Validaciones:

### name

* required
* minlength(3)
* maxlength(100)

### description

* required
* maxlength(500)

## HTML

* Utilizar clases CSS consistentes con los estilos definidos (se puede utilizar la libreria bootstrap).
* Si existe una hoja de estilos asociada, el HTML debe utilizar exactamente las clases esperadas por dicha hoja.
* No generar HTML sin clases si existe un CSS preparado para ellas.

## Estado de Carga

Durante la petición:

* Deshabilitar inputs.
* Deshabilitar botón.
* Mostrar indicador visual de carga.

## Respuestas

### Éxito

* Mostrar Toast de éxito.
* Limpiar formulario.

### Error

* Mostrar Toast de error.

## Restricciones

NO:

* Modificar rutas existentes.
* Modificar AppComponent.
* Agregar lógica de autenticación.
* Hardcodear tokens.
* Hardcodear URLs.
* Crear servicios duplicados.

## Resultado Esperado

Generar:

* ProjectService.
* CreateProjectComponent.
* HTML.
* CSS.
* Interfaces TypeScript necesarias.

El código debe compilar sin modificaciones adicionales.
