package com.andregarcia.kinalapp.controller;


import com.andregarcia.kinalapp.entity.Venta;
import com.andregarcia.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/ventas")
 // las rutas de este controlador empizan por /ventas
public class VentaController {
    private final IVentaService ventaService;
    public VentaController (IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Para peticiones GET
    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    // {codigoVenta} es el valor a buscar
    @GetMapping ("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigoVenta(@PathVariable int codigoVenta) {
        return ventaService.buscarPorCodigoVenta(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        try{
            Venta nuevaVenta = ventaService.guardarVenta(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Elimina una Venta
    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<String> eliminar (@PathVariable int codigoVenta) {
        try {
            if (!ventaService.existePorCodigoVenta(codigoVenta)) {
                return ResponseEntity.notFound().build();
            }
            ventaService.eliminarVenta(codigoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

        //Actualizar la venta a traves del Codigo
        @PutMapping("/{codigoVenta}")
        public ResponseEntity<?> actualizar(@PathVariable int codigoVenta, @RequestBody Venta venta){
            try {
                if (!ventaService.existePorCodigoVenta(codigoVenta)) {
                    return ResponseEntity.notFound().build();
                }
                Venta ventaActualizada = ventaService.actualizarVenta(codigoVenta, venta);
                return ResponseEntity.ok(ventaActualizada);
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }catch (RuntimeException e){
                return ResponseEntity.notFound().build();

            }
        }
}
