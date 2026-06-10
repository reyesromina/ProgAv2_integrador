Rol y Tecnología:
Actúa como un desarrollador Experto en Angular. Dominas la arquitectura limpia, componentes standalone y el manejo eficiente de estados mediante Angular Signals.

Contexto:
Ya contamos con la funcionalidad de registro. Ahora estamos implementando el inicio de sesión (Login) en la rama 'feature/auth-login'. Necesito que generes el código respetando la arquitectura de la app.

Especificación de la Feature:
[Aquí pegás el contenido de tu login-spec.md]

Restricciones Técnicas de Código:
1. Generar el `LoginComponent` como standalone con `ChangeDetectionStrategy.OnPush`.
2. Crear o actualizar el método `login` en el servicio de autenticación usando `HttpClient`.
3. El formulario reactivo debe validar el formato de email y requerir la contraseña.
4. Para mostrar los errores visuales en el HTML, utiliza un enfoque reactivo basado en el estado del formulario. No utilices llamadas a métodos comunes en las directivas estructurales del HTML para evitar problemas de rendimiento (ciclos de renderizado infinitos).
5. Integrar `ToastService` para los mensajes de alerta y `Router` para la redirección.

Formato de Salida:
Escribe estructuras de archivos separadas y limpias para:
- login.component.ts
- login.component.html
- auth.service.ts (método login)