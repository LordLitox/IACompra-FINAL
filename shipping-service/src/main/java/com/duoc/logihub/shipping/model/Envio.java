package com.duoc.logihub.shipping.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "envios")
@Data
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pedidoId;
    private String direccionDestino;
    private String transportista;
    private String estadoEnvio;

}