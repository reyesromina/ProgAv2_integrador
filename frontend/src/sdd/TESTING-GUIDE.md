# 🧪 GUÍA DE PRUEBAS Y VALIDACIÓN

**Componente:** Create Project Feature  
**Fecha:** 2026-06-10  
**Estado:** Listo para QA

---

## 📋 ÍNDICE DE PRUEBAS

1. [Validación de Compilación](#1-validación-de-compilación)
2. [Pruebas de Formulario](#2-pruebas-de-formulario)
3. [Pruebas de Navegación](#3-pruebas-de-navegación)
4. [Pruebas de API](#4-pruebas-de-api)
5. [Pruebas de Error Handling](#5-pruebas-de-error-handling)
6. [Pruebas de UI/UX](#6-pruebas-de-uiux)
7. [Pruebas de Performance](#7-pruebas-de-performance)
8. [Pruebas de Seguridad](#8-pruebas-de-seguridad)
9. [Pruebas de Accesibilidad](#9-pruebas-de-accesibilidad)

---

## 1. VALIDACIÓN DE COMPILACIÓN

### 1.1 Build Angular

**Paso a Paso:**
```powershell
cd frontend
npm run build
```

**Resultado Esperado:**
```
✅ Build Exitoso
- Sin errores TypeScript
- Sin errores Angular compiler
- Bundle creado en dist/
- Size: ~10-15 MB sin comprimir
```

**Validar:**
- [ ] Compilación completada sin errores
- [ ] No hay warnings críticos
- [ ] Archivos en dist/ existen
- [ ] Create-project chunk generado

### 1.2 Lint TypeScript

**Paso a Paso:**
```powershell
npm run lint  # Si existe
# O manualmente con ng lint
```

**Validar:**
- [ ] No hay errores lint
- [ ] Tipos correctos en todo el código
- [ ] Imports utilizados
- [ ] No hay variables no usadas

---

## 2. PRUEBAS DE FORMULARIO

### 2.1 Campos Requeridos

**Test Case: TC-001**
```
Titulo: Nombre Requerido
Pasos:
  1. Navegar a /proyectos/crear
  2. Dejar campo "Nombre" vacío
  3. Click fuera del campo (blur)
  4. Intentar enviar formulario

Resultado Esperado:
  ✓ Mensaje: "El nombre es requerido"
  ✓ Input tiene borde rojo
  ✓ Botón está deshabilitado
```

**Test Case: TC-002**
```
Titulo: Descripción Requerida
Pasos:
  1. Navegar a /proyectos/crear
  2. Ingresaré nombre válido
  3. Dejar descripción vacía
  4. Click fuera del campo
  5. Intentar enviar

Resultado Esperado:
  ✓ Mensaje: "La descripción es requerida"
  ✓ Textarea tiene borde rojo
  ✓ Botón está deshabilitado
```

### 2.2 Longitud Mínima

**Test Case: TC-003**
```
Titulo: Nombre Demasiado Corto
Pasos:
  1. Navegar a /proyectos/crear
  2. Ingresar "AB" en nombre
  3. Click fuera del campo
  4. Intentar enviar

Resultado Esperado:
  ✓ Mensaje: "El nombre debe tener al menos 3 caracteres"
  ✓ Botón deshabilitado
```

### 2.3 Longitud Máxima

**Test Case: TC-004**
```
Titulo: Nombre Demasiado Largo
Pasos:
  1. Copiar string de 101 caracteres
  2. Pegarlo en nombre
  3. Verificar estado

Resultado Esperado:
  ✓ Input solo acepte 100 caracteres
  ✓ Mensaje: "El nombre no puede exceder 100 caracteres"
  ✓ Botón deshabilitado
```

**Test Case: TC-005**
```
Titulo: Descripción Demasiado Larga
Pasos:
  1. Copiar string de 501 caracteres
  2. Pegarlo en descripción
  3. Verificar estado

Resultado Esperado:
  ✓ Textarea solo acepte 500 caracteres
  ✓ Mensaje: "La descripción no puede exceder 500 caracteres"
  ✓ Botón deshabilitado
```

### 2.4 Valores Válidos

**Test Case: TC-006**
```
Titulo: Nombre Válido (3 caracteres)
Pasos:
  1. Ingresar "ABC" en nombre
  2. Ingresar descripción válida
  3. Verificar estado

Resultado Esperado:
  ✓ Sin mensaje de error
  ✓ Input sin borde rojo
  ✓ Botón habilitado (si descripción también válida)
```

**Test Case: TC-007**
```
Titulo: Nombre Válido (100 caracteres)
Pasos:
  1. Ingresar string exacto de 100 caracteres
  2. Ingresar descripción válida
  3. Verificar estado

Resultado Esperado:
  ✓ Sin mensaje de error
  ✓ Botón habilitado
```

**Test Case: TC-008**
```
Titulo: Descripción Válida (500 caracteres)
Pasos:
  1. Ingresar nombre válido
  2. Ingresar string exacto de 500 caracteres
  3. Verificar estado

Resultado Esperado:
  ✓ Sin mensaje de error
  ✓ Botón habilitado
```

---

## 3. PRUEBAS DE NAVEGACIÓN

### 3.1 Acceso a Ruta

**Test Case: TC-009**
```
Titulo: Navegar a /proyectos/crear
Pasos:
  1. Estar autenticado
  2. Navegar a http://localhost:4200/proyectos/crear
  3. Verificar que componente cargue

Resultado Esperado:
  ✓ Página se muestra correctamente
  ✓ Formulario visible
  ✓ No hay errores en consola
```

### 3.2 Ruta No Existente

**Test Case: TC-010**
```
Titulo: Navegar a ruta inválida
Pasos:
  1. Navegar a /proyectos/xxx
  2. Verificar redirección

Resultado Esperado:
  ✓ Redirige a /login (wildcard)
```

### 3.3 Volver Atrás

**Test Case: TC-011**
```
Titulo: Botón Volver (en error)
Pasos:
  1. Inducir error (ej: server error)
  2. Click "Intentar de Nuevo"
  3. Verificar regreso a formulario

Resultado Esperado:
  ✓ Formulario vuelve a mostrar
  ✓ Estado = 'formulario'
  ✓ Campos vacíos
```

---

## 4. PRUEBAS DE API

### 4.1 Llamada API Exitosa

**Test Case: TC-012**
```
Titulo: Crear Proyecto Exitosamente
Pasos:
  1. Completar formulario correctamente
    - Nombre: "Mi Proyecto"
    - Descripción: "Una descripción válida"
  2. Click "Crear Proyecto"
  3. Verificar request HTTP

Resultado Esperado:
  ✓ POST /users/projects enviado
  ✓ Payload correcto:
    {
      "name": "Mi Proyecto",
      "description": "Una descripción válida",
      "projectStatus": "ACTIVE"
    }
  ✓ Response 200 OK recibido
  ✓ Toast: "Proyecto creado exitosamente"
  ✓ Redirige a /proyectos (2s después)
```

### 4.2 Validar Headers

**Test Case: TC-013**
```
Titulo: Headers HTTP Correctos
Pasos:
  1. Abrir DevTools (F12)
  2. Network tab
  3. Crear proyecto
  4. Ver request headers

Resultado Esperado:
  ✓ Authorization: Bearer {token}
  ✓ Content-Type: application/json
  ✓ Otros headers estándar
```

### 4.3 Validar Payload

**Test Case: TC-014**
```
Titulo: Payload JSON Correcto
Pasos:
  1. DevTools > Network
  2. Ver request body
  3. Verificar JSON

Resultado Esperado:
  ✓ JSON válido
  ✓ name: string correcto
  ✓ description: string correcto
  ✓ projectStatus: "ACTIVE"
  ✓ No campos extra
```

---

## 5. PRUEBAS DE ERROR HANDLING

### 5.1 Error 400 Bad Request

**Test Case: TC-015**
```
Titulo: Manejar Error 400
Pasos:
  1. Backend configurado para retornar 400
  2. Enviar proyecto
  3. Verificar respuesta

Resultado Esperado:
  ✓ State: 'error'
  ✓ Toast: "Datos inválidos..."
  ✓ Alert rojo mostrado
  ✓ Botón "Intentar de Nuevo"
```

### 5.2 Error 401 Unauthorized

**Test Case: TC-016**
```
Titulo: Manejar Error 401
Pasos:
  1. Token expirado o inválido
  2. Enviar proyecto
  3. Verificar respuesta

Resultado Esperado:
  ✓ State: 'error'
  ✓ Toast: "No estás autenticado"
  ✓ Mensaje claro
  ✓ Opción de reintentar
```

### 5.3 Error 500 Server Error

**Test Case: TC-017**
```
Titulo: Manejar Error 500
Pasos:
  1. Backend retorna 500
  2. Enviar proyecto
  3. Verificar respuesta

Resultado Esperado:
  ✓ State: 'error'
  ✓ Toast: "Error al crear el proyecto"
  ✓ Usuario puede reintentar
```

### 5.4 Error de Red

**Test Case: TC-018**
```
Titulo: Manejar Error de Conectividad
Pasos:
  1. Desconectar internet
  2. Enviar proyecto
  3. Verificar manejo

Resultado Esperado:
  ✓ State: 'error'
  ✓ Toast de error genérico
  ✓ No crash de app
  ✓ Puede reintentar
```

---

## 6. PRUEBAS DE UI/UX

### 6.1 Estado de Carga

**Test Case: TC-019**
```
Titulo: Indicador de Carga Visible
Pasos:
  1. Enviar proyecto
  2. Verificar estado durante request

Resultado Esperado:
  ✓ Spinner animado visible
  ✓ Inputs deshabilitados
  ✓ Botón deshabilitado
  ✓ Texto: "Creando..."
```

### 6.2 Animaciones

**Test Case: TC-020**
```
Titulo: Animaciones Suaves
Pasos:
  1. Esperar a respuesta exitosa
  2. Observar transición

Resultado Esperado:
  ✓ Fade-in suave (0.3s)
  ✓ Ícono de éxito con scale
  ✓ No es jarring
```

### 6.3 Mensajes de Error Dinámicos

**Test Case: TC-021**
```
Titulo: Mostrar Error del Server
Pasos:
  1. Backend retorna error con mensaje
  2. Verificar Toast

Resultado Esperado:
  ✓ Mensaje exacto mostrado
  ✓ No mensaje genérico
```

### 6.4 Limpiar Formulario

**Test Case: TC-022**
```
Titulo: Limpiar después de Éxito
Pasos:
  1. Crear proyecto exitosamente
  2. En estado 'exito', verificar formulario

Resultado Esperado:
  ✓ Campos = vacío
  ✓ Validaciones resetean
```

---

## 7. PRUEBAS DE PERFORMANCE

### 7.1 Bundle Size

**Test Case: TC-023**
```
Titulo: Verificar Bundle Size
Pasos:
  1. npm run build
  2. Revisar dist/
  3. Tamaño del chunk

Resultado Esperado:
  ✓ create-project-component < 15KB
  ✓ Gzip < 5KB
```

### 7.2 Lazy Loading

**Test Case: TC-024**
```
Titulo: Componente Lazy Load
Pasos:
  1. Build app
  2. DevTools Network
  3. Navegar a /proyectos/crear
  4. Ver si chunk se descarga

Resultado Esperado:
  ✓ Chunk descargado solo al navegar
  ✓ No está en main bundle
```

### 7.3 Change Detection

**Test Case: TC-025**
```
Titulo: Detección de Cambios Eficiente
Pasos:
  1. DevTools Profiler
  2. Cambiar input
  3. Revisar ciclos de detección

Resultado Esperado:
  ✓ OnPush funciona correctamente
  ✓ Solo se ejecuta cuando cambia signal
```

---

## 8. PRUEBAS DE SEGURIDAD

### 8.1 Token Authentication

**Test Case: TC-026**
```
Titulo: Token Agregado Automáticamente
Pasos:
  1. DevTools Network
  2. Ver request headers
  3. Verificar Authorization

Resultado Esperado:
  ✓ Bearer token presente
  ✓ Token válido
  ✓ AuthInterceptor funciona
```

### 8.2 XSS Prevention

**Test Case: TC-027**
```
Titulo: Prevenir XSS
Pasos:
  1. Ingresar en nombre: <script>alert('xss')</script>
  2. Enviar proyecto
  3. Verificar en response

Resultado Esperado:
  ✓ Script no se ejecuta
  ✓ Angular sanitiza automáticamente
```

### 8.3 CSRF Prevention

**Test Case: TC-028**
```
Titulo: CSRF Token (si aplica)
Pasos:
  1. Revisar request headers
  2. Verificar token CSRF

Resultado Esperado:
  ✓ Token presente (si backend requiere)
  ✓ Se envía en cada request
```

---

## 9. PRUEBAS DE ACCESIBILIDAD

### 9.1 Labels Correctos

**Test Case: TC-029**
```
Titulo: Labels Asociados a Inputs
Pasos:
  1. DevTools Accessibility
  2. Inspeccionar inputs
  3. Verificar labels

Resultado Esperado:
  ✓ Cada input tiene label
  ✓ Label linked con for/id
```

### 9.2 Navegación por Teclado

**Test Case: TC-030**
```
Titulo: Tab Navigation
Pasos:
  1. Presionar Tab
  2. Navegar por formulario
  3. Presionar Enter para enviar

Resultado Esperado:
  ✓ Focus visible
  ✓ Tab order lógico
  ✓ Enter envía formulario
```

### 9.3 Contrast de Color

**Test Case: TC-031**
```
Titulo: Contraste de Colores
Pasos:
  1. DevTools Lighthouse
  2. Revisar Accessibility
  3. Verificar contrast

Resultado Esperado:
  ✓ WCAG 2.1 AA compliance
  ✓ Text readable
```

---

## 📊 CHECKLIST DE VALIDACIÓN

### Pre-Deployment
- [ ] Build exitoso sin errores
- [ ] TypeScript tipos correctos
- [ ] Todas las pruebas TC-001 a TC-031 pasaron
- [ ] No hay console errors
- [ ] Performance acceptable

### Funcionalidad
- [ ] Formulario valida correctamente
- [ ] API se llama con payload correcto
- [ ] Errores se manejan apropiadamente
- [ ] Navegación funciona
- [ ] Estados visuales correctos

### Seguridad
- [ ] Token authentication funciona
- [ ] XSS prevenido
- [ ] CSRF protection (si aplica)
- [ ] No se exponen secrets

### UX/UI
- [ ] Interfaz limpia
- [ ] Mensajes claros
- [ ] Animaciones suaves
- [ ] Responsive design
- [ ] Accesibilidad básica

---

## 🚀 DEPLOYMENT CHECKLIST

**Antes de ir a Producción:**
- [ ] Código revisado por otro dev
- [ ] Tests unitarios agregados
- [ ] Integración con backend verificada
- [ ] Environment variables configuradas
- [ ] Logs agregados para debugging
- [ ] Error handling exhaustivo
- [ ] Documentación actualizada
- [ ] QA team aprobó

---

## 📝 REPORTE DE PRUEBAS

### Template para Documentar Resultados

```
REPORTE DE PRUEBAS - Create Project Feature
============================================
Fecha: [fecha]
Tester: [nombre]
Versión: [v]

RESUMEN:
- Total Test Cases: 31
- Pasados: [X]
- Fallidos: [Y]
- Bloqueados: [Z]

RESULTADOS POR CATEGORÍA:
1. Compilación: ✅ Pasado
2. Formulario: ✅ Pasado
3. Navegación: ✅ Pasado
4. API: ✅ Pasado
5. Error Handling: ✅ Pasado
6. UI/UX: ✅ Pasado
7. Performance: ✅ Pasado
8. Seguridad: ✅ Pasado
9. Accesibilidad: ⚠️ Parcial

OBSERVACIONES:
[Detalles de cualquier issue]

RECOMENDACIONES:
[Mejoras sugeridas]

APROBACIÓN: ✅ / ❌ / ⏳
```

---

## 📞 SOPORTE

Si encuentras problemas durante testing:

1. **Revisar documentación:**
   - IMPLEMENTATION-REPORT.md
   - DECISIONS-MATRIX.md
   - ARCHITECTURE-DIAGRAMS.md

2. **Verificar browser console:**
   - Errores TypeScript
   - Errores HTTP
   - Warnings

3. **Usar DevTools:**
   - Network tab (HTTP requests)
   - Console (errors/logs)
   - Application (local storage)

---

**Última actualización:** 2026-06-10  
**Versión:** 1.0
