# Generación de Frontend Angular

Soy un desarrollador trainee de Angular 21 con experiencia en aplicaciones web.

## Contexto del sistema

Estoy desarrollando el frontend de una aplicación de gestión de usuarios y órdenes.

El backend es una API REST desarrollada en Java.

El frontend utiliza:

* Angular 21
* Standalone Components
* Bootstrap 5
* Reactive Forms
* Signals
* HttpClient

## Feature a implementar

Registro de usuarios.

Utilizar como especificación funcional el contenido completo del archivo:

```text
SPEC.md
```

La implementación debe respetar estrictamente el comportamiento definido en dicho documento.

No asumir funcionalidades que no estén descritas en el SPEC.

---

## Restricciones técnicas

### Angular

* Angular 21.
* Standalone Components únicamente.
* ChangeDetectionStrategy.OnPush.
* Tipado estricto.

### UI

* Bootstrap 5 nativo.
* Diseño responsive.
* Sin librerías visuales adicionales.

### Formularios

Utilizar Reactive Forms.

Validaciones mínimas:

#### Email

* Campo obligatorio.
* Formato email válido.

#### Contraseña

* Campo obligatorio.

No implementar reglas adicionales que no estén definidas en el SPEC.

---

## Servicios

Toda la comunicación HTTP debe encapsularse dentro de:

```text
src/app/services/usuario.service.ts
```

El servicio deberá incluir:

```typescript
registerUser(email: string, password: string)
```

y las interfaces correspondientes para Request y Response.

---

## Manejo de estados

Utilizar Signals para administrar:

* Estado de carga.
* Estado de éxito.
* Estado de error.

Flujo esperado:

```text
Formulario
   ↓
Enviando solicitud
   ↓
Registro exitoso
```

o

```text
Formulario
   ↓
Enviando solicitud
   ↓
Error
```

---

## Comportamiento esperado

Cuando el usuario presione "Registrarse":

1. Validar formulario.
2. Invocar el endpoint definido en el SPEC.
3. Mostrar indicador de carga.
4. Deshabilitar el botón de envío.
5. Procesar respuesta.

Si el registro es exitoso:

* Mostrar mensaje de confirmación.
* Permitir navegar posteriormente a:

```text
/login/user
```

Si ocurre un error:

* Mostrar mensaje genérico.
* Mantener los datos ingresados.

---

## Formato de salida esperado

Generar de forma limpia, modular y comentada:

### 1. registro.component.ts

Debe incluir:

* Standalone Component.
* ChangeDetectionStrategy.OnPush.
* Reactive Forms.
* Signals.
* Integración con UsuarioService.

### 2. registro.component.html

Debe incluir:

* Bootstrap 5.
* Formulario de registro.
* Validaciones visuales.
* Estado de carga.
* Estado de éxito.
* Estado de error.

### 3. usuario.service.ts

Debe incluir:

* HttpClient.
* Interfaces Request y Response.
* Método registerUser().
* Manejo tipado de respuestas.

### Explicación adicional

Explicar:

* Cómo registrar la ruta.
* Cómo utilizar los Signals.
* Cómo consumir UsuarioService desde el componente.

Aplicar buenas prácticas de Angular 21, separación de responsabilidades y código mantenible.
