package com.andregarcia.kinalapp.controller;

import ch.qos.logback.core.net.server.Client;
import com.andregarcia.kinalapp.entity.Cliente;
import com.andregarcia.kinalapp.repository.ClienteRepository;
import com.andregarcia.kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RestController =  @Controller + @RequestBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador deben empezar por /clientes
public class ClienteController<cliente> {
    // Inyectamos el SERVICIO y NO el repositorio
    // El controlador solo debe de tener conexión con el Servicio
    private final IClienteService clienteService;
    // Como buena practica la Indecision de dependencias debe hacerse por el constructor
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Responde peticiones GET
     @GetMapping
     // ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listarTodos();
        // Delegamos al servicio
         return ResponseEntity.ok(clientes);
         // 200 OK con la lista de clientes
     }
    //{dpi} es una variable de ruta (valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        // @PathVariable toma el valor de la URL y lo asigna al DPI
        return clienteService.buscarPorDPI(dpi)
        // Si optional tiene valor, devolve 200 ok con el cliente
        .map(ResponseEntity::ok)
        // Si optional esta vacío, devolve 404 Not FOUND
        .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por el estado del cliente
    @GetMapping("/{estado}")
    public ResponseEntity<Cliente> buscarPorActivos(@PathVariable int estado) {
            return clienteService.buscarPorActivos(estado);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente){
        // @RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        // <?> significa: "tipo generico" puede ser un Cliente o un String
        try{
            Cliente nuevoCliente = clienteService.guardar(cliente);
            // Intentamos guardar el cliente, pero puede lanzar una exception
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            // 201 CREATED (mucho más especifico que el 200 para la creacion de un cliente)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // Delete elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar (@PathVariable String dpi){
        // ResponsseEntity<Void>: No devuelve cuerpo en la respuesta
        try{
            if (!clienteService.existePorDPI(dpi)){
                return ResponseEntity.notFound().build();
                // 404 Si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT (se ejecutó correctamente y no devuelve cuerpo)

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            // 404 NOT FOUND

        }
    }
    // Actualizar cliente a traves del DPI
     @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try {
            if (!clienteService.existePorDPI(dpi)){
            // Verificar si existe antes de poder actualizar
            return ResponseEntity.notFound().build();
            // 404 Not Found
        }
            // Actualizamos el cliente, pero esto puede lanzar una exception
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            // 200 ok con el cliente ya actualizado

        }catch (IllegalArgumentException e){
            // Error cuando los datos sean incorrectos
            return  ResponseEntity.badRequest().body(e.getMessage());

        }catch (RuntimeException e){
            // Posiblemente cualquiera otro error como: cliente no encontrado etc.
            // 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }

     }

}