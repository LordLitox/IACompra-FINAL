package com.duoc.logihub.inventory.model;

import jakarta.persistence.*; // Importa las anotaciones de persistencia 
import lombok.Data;

@Entity // Indica que esta clase será una tabla persistente en la BD 
@Table(name = "inventarios") // Define el nombre físico de la tabla en MySQL 
@Data // Lombok: genera getters, setters y toString automáticamente
public class Inventario {

    @Id // Define la clave primaria 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID se genera solo en MySQL 
    private Long id;

    @Column(nullable = false) // Configura la columna para que no acepte nulos 
    private Long productoId;

    @Column(nullable = false)
    private int cantidad;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100) // Podemos limitar el largo del texto en la BD
    private String bodega;
}