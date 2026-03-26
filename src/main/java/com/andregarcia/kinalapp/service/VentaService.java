package com.andregarcia.kinalapp.service;

import java.util.Optional;
import java.util.List;

import com.andregarcia.kinalapp.repository.VentaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andregarcia.kinalapp.entity.Venta;
import com.andregarcia.kinalapp.repository.ClienteRepository;

@Service
@Transactional
public class VentaService implements IVentaService{

    private final VentaRepository ventaRepository;
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        validarVenta(venta);
        if (venta.getEstado() == 0)
            venta.setEstado(1);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(int codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    public Venta actualizarVenta(int codigoVenta, Venta venta) {
        if (!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontro con el codigo " + codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    //Para eliminar una venta
    public void eliminarVenta(int codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontro con el codigo " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional
    public boolean existePorCodigoVenta(int codigoVenta){
        return ventaRepository.existsById(codigoVenta);
    }

    //Metodo privado
    private void validarVenta(Venta venta){
        if (venta.getCodigoVenta() <= 0) {
            throw new IllegalArgumentException("El código de venta debe ser mayor a 0.");
        }

        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria.");
        }

        if (venta.getTotal() == null || venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total es obligatorio y debe ser mayor a 0.");
        }

    }

}

