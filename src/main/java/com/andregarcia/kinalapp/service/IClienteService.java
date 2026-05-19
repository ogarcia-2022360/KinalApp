package com.andregarcia.kinalapp.service;

import com.andregarcia.kinalapp.entity.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    /*
     * Interfaz: Es un contrato que dice que metodos debe tener
     * cualquier servicio de Clientes, No tiene
     * Implementacion solo la definicion de los metodos
     */

    //Metodo que devuelve una lista de todos los Clientes
    List<Cliente> listarTodos();
    /*
     * Lista<Cliente> lo que hace es devolver una lista
     * de objetos de la entidad Clientes
     */

    //Metodo que guarda un Cliente en la DB
    Cliente guardar(Cliente cliente);
    //Parameteos: Recibe un objeto Cliente con los datos de
    //guardar

    //Optional - Contenedor que puede o no tener valor
    //evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);

    //MetODO QUE ACTUALIZA UN cLIENTE
    Cliente actualizar(String dpi, Cliente cliente);
    /*
     * Parameteos - dpi: DPI del cliente a actualizar
     * Cliente cliente: Objeto con los datos nuevos
     * Retorna un objeto de cliente ya actualizado
     * */

    /*
     * Metodo de tipo void para eliminar a un Cliente
     * void: no retorna ningun valor o dato
     * Elimina un Cliente por su dpi
     */
    void eliminar(String dpi);

    //boolean - Retorna true si existe y false si no existe
    boolean existePorDPI(String dpi);

    ResponseEntity<Cliente> buscarPorActivos(int estado);
}
