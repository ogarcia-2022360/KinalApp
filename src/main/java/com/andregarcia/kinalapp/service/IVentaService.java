package com.andregarcia.kinalapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.andregarcia.kinalapp.entity.Venta;

public interface IVentaService {

    //Metodo que devuelve una lista de todos las Ventas
    List<Venta> listarVentas();

    //Metodo que guarda una Venta en la DB
    Venta guardarVenta(Venta venta);

    Optional<Venta> buscarPorCodigoVenta(int codigoVenta);

    //Metodo que actualiza una Venta
    Venta actualizarVenta(int codigoVenta, Venta venta);

    //Metodo de tipo void para eliminar una Venta
    void eliminarVenta(int codigoVenta);

    //
    boolean existePorCodigoVenta (int codigoVenta);
}

