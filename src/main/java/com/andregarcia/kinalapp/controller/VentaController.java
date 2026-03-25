package com.andregarcia.kinalapp.controller;


import com.andregarcia.kinalapp.entity.Cliente;
import com.andregarcia.kinalapp.entity.Venta;
import com.andregarcia.kinalapp.service.IClienteService;
import com.andregarcia.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/ventas")
public class VentaController <venta> {
    private final IVentaService ventaService;
    public VentaController (IVentaService ventaService) {
        this.ventaService = ventaService
    }

    // Para peticiones GET
    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listar();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping ("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigoVenta(@PathVariable int codigoVenta) {
        return ventaService.buscarPorCodigoVenta(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@ResponseBody Venta venta){
        try{
            Venta nuevaVenta = ventaService.guardar(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<void> eliminar (@PathVariable int codigoVenta) {
        try{
            if (!ventaService.existePorCodigoVenta(codigoVenta)){
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminar(codigoVenta);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

        @PutMapping("/{codigoVenta}")
                public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Venta venta){
            try {
                if (!IClienteService.existePorCodigoVenta(codigoVenta)) {
                    return ResponseEntity.notFound().build();
                }
                Cliente ventaActualizada = ventaService.actualizar(codigoVenta, venta);
            }catch (IllegalArgumentException e){

            }
        }

        }
}