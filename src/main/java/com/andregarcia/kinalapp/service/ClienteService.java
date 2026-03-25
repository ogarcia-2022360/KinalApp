package com.andregarcia.kinalapp.service;

import com.andregarcia.kinalapp.entity.Cliente;
import com.andregarcia.kinalapp.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 * Anotacion que registra un Bean como un Bean de Spring
 * Que la clase contiene la logica del negocion
 */

@Service
/*
 * Por defecto todos los metodos de esta clase seran transaccionales
 * Una transaccion es que puede o no ocurrir algo
 */

@Transactional
public class ClienteService implements IClienteService{

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Indica que se está implementando un metodo de la interfaz
    @Override
    /// optimizar la consulta solo lectura, para que no bloquee la BD
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
        //findAll() es un metodo de spring que hace el select * from Clientes
        //este metodo es de JPARepository
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        /*
         * Metodo de guardar  crea un Cliente
         * Aca es donde colocamos la logica del negocio antes de guardar
         * primero validamos el dato
         */
        validarCliente(cliente);
        if (cliente.getEstado() == 0)
            cliente.setEstado(1);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorDPI(String dpi) {
        //Buscar un cliente por DPI
        return clienteRepository.findById(dpi);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Cliente actualizar(String dpi, Cliente cliente) {
        //Meto para actualizar un cliente existente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException("El cliente no se encontro con el dpi "+dpi);
            // si no existe se lanza una exepcion (error controlado)

        }
        cliente.setDPICliente(dpi);
        // Asegurarnos que el dpi del objeto coincida con el de la URL
        //Por seguridad usamos el dpi de la URL y no el que viene en el JSON
        validarCliente(cliente);
        return clienteRepository.save(cliente);
        /*
         * save() este no solo sirve para guardar sino tambien para actualizar Si el dato existe el dato
         * Existe (dpi) entonces hace UPDATE pero si no existe hace un INSERT
         * antes verificamos si existe o no el registro
         */
    }

    @Override
    public void eliminar(String dpi) {
        //Eliminar un cliente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException( "El cliente no se encontro con el dpi" + dpi);
        }
        clienteRepository.deleteById(dpi);
    }

    @Override
    @Transactional
    public boolean existePorDPI(String dpi) {
        // Verifica si existe un cliente
        return clienteRepository.existsById(dpi);
    }

    @Override
    public ResponseEntity<Cliente> buscarPorActivos(int estado) {
        return (ResponseEntity<Cliente>) clienteRepository.findAll();
    }

    // Metodo privado (solo puede utilizarse dentro de la clase)
    private void validarCliente(Cliente cliente){
        /*
         * Validaciones del negocio: Este metodo se hara privado porque
         * es algo interno del servicio
         */
        if(cliente.getDPICliente() == null || cliente.getDPICliente().trim().isEmpty()){
            //si el DPI es null o esta vacio después de quitar espacios
            //lanza una execepcion con un mensaje
            throw new IllegalArgumentException("El DPI es un dato obligatorio.");

        }
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio.");
        }

        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty()){
            new IllegalArgumentException("El apellido es un dato obligatorio.");
        }
    }

}
