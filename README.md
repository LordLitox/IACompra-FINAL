LogiHub - Sistema Distribuido de Gestión Logística y Ventas
 Descripción del Proyecto
LogiHub es un ecosistema de microservicios diseñado para automatizar y optimizar el ciclo completo de una plataforma logística y de comercio electrónico. La solución abarca desde el control estricto de existencias físicas en múltiples bodegas hasta el seguimiento en tiempo real del despacho al cliente final, garantizando la consistencia y el desacoplamiento de datos mediante comunicación sincrónica.

El proyecto está construido bajo una arquitectura orientada a microservicios utilizando Spring Boot, Spring Cloud OpenFeign para la intercomunicación y persistencia independiente por componente.

Arquitectura del EcosistemaEl flujo de información sigue un patrón coreográfico basado en responsabilidades bien delimitadas:
$$\text{Catalog/Users} \longrightarrow \text{Orders} \longrightarrow \text{Inventory} \longrightarrow \text{Payments} \longrightarrow \text{Shipping/Tracking/Notifications/Reviews}$$
Componentes y Responsabilidades

El sistema se compone de los siguientes **9 microservicios** independientes:

1. **`auth-service`**: Gestiona la seguridad perimetral, autenticación de credenciales y emisión/validación de tokens JWT.
2. **`users-service`**: Administra la información comercial de los clientes, perfiles y las **direcciones de despacho** críticas para la entrega.
3. **`catalog-service`**: Expone la cara comercial de la plataforma (nombres de productos, descripciones, categorías y precios).
4. **`inventory-service` (Puerto 8084)**: Controla las existencias físicas y el stock por bodegas (`inventory_db`). Responde de forma sincrónica a las solicitudes de reserva.
5. **`orders-service` (Puerto 8085)**: Cerebro transaccional. Coordina la creación del pedido (estados `PENDIENTE`/`APROBADO`) y valida el stock con el inventario antes de permitir el pago.
6. **`payments-service` (Puerto 8086)**: Orquestador del flujo post-pago. Valida el cobro y, de ser exitoso, gatilla mediante Feign la aprobación de la orden y la creación del envío.
7. **`shipping-service` (Puerto 8087)**: Gestiona la logística interna de despacho, asignación de transportistas y emisión de guías.
8. **`tracking-service`**: Registra el historial de rutas y eventos públicos del paquete para el cliente final.
9. **`notification-service`**: Consume los eventos del sistema para generar alertas y confirmaciones en consola de cara al usuario.
10. **`reviews-service`**: Permite a los usuarios calificar y dejar comentarios (feedback) sobre los productos adquiridos o la calidad del servicio de despacho, cerrando el ciclo de experiencia del cliente.

Tecnologías Utilizadas
Backend: Java 17+ / Spring Boot 3.x

Comunicación: Spring Cloud OpenFeign (Rest Clients)

Persistencia: Spring Data JPA / Hibernate

Bases de Datos: Relacionales independientes por servicio (MySQL / Oracle / H2)

Validación: Jakarta Validation (@Valid, @NotNull, @NotBlank)

1.-Requisitos Previos:

JDK 17 o superior instalado.

Apache Maven configurado.

Servidor de Base de Datos activo.

2.-Variables en application.properties (Ejemplo General)
Cada microservicio debe contar con su base de datos y puerto asignado en su respectivo archivo de propiedades:

Properties
server.port=808X
spring.application.name=nombre-service

spring.datasource.url=jdbc:mysql://localhost:3306/nombre_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena
spring.jpa.hibernate.ddl-auto=update

Guía de Pruebas de Integración (Postman)
Para verificar el correcto funcionamiento del ecosistema y la resiliencia entre servicios, ejecute las peticiones en el siguiente orden secuencial:

Paso 1: Abastecer Bodega (inventory-service)
Método: POST

URL: http://localhost:8084/api/inventory/update

Payload:

JSON
{
    "productoId": 10,
    "nombre": "Laptop Pro 16",
    "cantidad": 50,
    "bodega": "Valparaíso"
}
Paso 2: Generar la Orden (orders-service)
El servicio restará automáticamente las unidades del inventario.

Método: POST

URL: http://localhost:8085/api/orders

Payload:

JSON
{
    "clienteId": 1,
    "productoId": 10,
    "cantidad": 2,
    "total": 95000
}
Respuesta esperada: Estado PENDIENTE e ID de orden generado (ej: 1).

Paso 3: Procesar Pago y Automatización (payments-service)
Al aprobarse el pago, el servicio disparará llamadas automáticas concurrentes actualizando la orden a APROBADO y creando el envío en shipping-service.

Método: POST

URL: http://localhost:8086/api/payments/process

Payload:

JSON
{
    "pedidoId": 1,
    "monto": 95000,
    "metodo": "Webpay"
}

Paso 4: Verificación del Efecto Dominó
Orders (GET http://localhost:8085/api/orders/1): Debe figurar como APROBADO.

Inventory (GET http://localhost:8084/api/inventory): El stock del producto 10 debe haber disminuido a 48.

Shipping (GET http://localhost:8087/api/shipping): Debe existir un registro de envío en estado EN_PREPARACION mapeado al pedido 1.
