package com.andregarcia.kinalapp.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ventas")

public class Venta {
@Id
@Column (name = "codigo_venta")
private int codigoVenta;
@Column
private Date fechaVenta;
@Column
private Double total;
@Column
private int estado;
@ManyToOne
@JoinColumn (name = "clientes_dpi_usuario")
    private String dpiCliente;
@JoinColumn (name = "codigo_usuario")
    private String codigoUsuario;

    public Venta() {
    }

    public Venta(String codigoUsuario, int codigoVenta, String dpiCliente, int estado, Date fechaVenta, Double total) {
        this.codigoUsuario = codigoUsuario;
        this.codigoVenta = codigoVenta;
        this.dpiCliente = dpiCliente;
        this.estado = estado;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(int codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

