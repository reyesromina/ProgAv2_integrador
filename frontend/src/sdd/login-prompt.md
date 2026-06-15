Rol y Tecnología:
Actúa como un desarrollador Experto en Angular. Dominas la arquitectura limpia, componentes standalone y el manejo eficiente de estados mediante Angular Signals.

Contexto:
Ya contamos con la funcionalidad de registro. Ahora estamos implementando el inicio de sesión (Login) en la rama 'feature/auth-login'. Necesito que generes el código respetando la arquitectura de la app.



Restricciones Técnicas de Código:
1. Generar el `LoginComponent` como standalone con `ChangeDetectionStrategy.OnPush`.
2. Crear o actualizar el método `login` en el servicio de autenticación usando `HttpClient`.
3. El formulario reactivo debe validar el formato de email y requerir la contraseña.
4.4. Para mostrar errores de validación en el HTML, expón propiedades 
   computadas con `computed()` de Angular Signals en el componente 
   (ej: emailInvalid = computed(() => ...)). Nunca invoques métodos 
   ni accedas a `this.form.get(...)` directamente en el template.
5. Integrar `ToastService` para los mensajes de alerta y `Router` para la redirección.

Formato de Salida:
Escribe estructuras de archivos separadas y limpias para:
- login.component.ts
- login.component.html
- auth.service.ts (método login)