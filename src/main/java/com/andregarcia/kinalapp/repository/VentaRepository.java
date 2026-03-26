package com.andregarcia.kinalapp.repository;

import com.andregarcia.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository <Venta, Integer> {
}

