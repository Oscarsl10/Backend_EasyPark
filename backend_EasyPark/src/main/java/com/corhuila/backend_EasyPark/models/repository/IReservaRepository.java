package com.corhuila.backend_EasyPark.models.repository;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IReservaRepository extends CrudRepository<Reserva, Long> {
    List<Reserva> findByStatusTrue();
    List<Reserva> findByFechaFinBeforeAndStatusTrue(Date fecha);

    @Query("SELECT r FROM Reserva r WHERE r.vehiculo.placa = :placa AND r.confirmado = true AND r.pago IS NULL")
    List<Reserva> findReservasConfirmadasPorPlaca(@Param("placa") String placa);

    // Método para obtener reservas confirmadas por placa del vehículo
    List<Reserva> findByConfirmadoAndVehiculo_Placa(Boolean confirmado, String placa);

    // Método para obtener todas las reservas confirmadas
    List<Reserva> findByConfirmado(Boolean confirmado);
}
