package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.EspacioTotal;
import com.corhuila.backend_EasyPark.models.repository.IEspacioTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EspacioTotalServiceImpl implements IEspacioTotalService {

    @Autowired
    private IEspacioTotalRepository espacioTotalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EspacioTotal> findAll(){
        return espacioTotalRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public EspacioTotal findById(Long id){
        EspacioTotal espacio = espacioTotalRepository.findById(id).orElse(null);
        return (espacio != null && Boolean.TRUE.equals(espacio.getStatus())) ? espacio : null;
    }

    @Override
    @Transactional
    public EspacioTotal save(EspacioTotal espacio) {
        if (espacio.getId() != null) {
            EspacioTotal existente = espacioTotalRepository.findById(espacio.getId())
                    .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

            int totalAnterior = existente.getEspacio_total();
            int disponiblesAnteriores = existente.getDisponibles() != null ? existente.getDisponibles() : totalAnterior;
            int reservados = totalAnterior - disponiblesAnteriores;

            int nuevoTotal = espacio.getEspacio_total();

            if (nuevoTotal < reservados) {
                throw new RuntimeException("No se puede reducir el total por debajo de los espacios reservados (" + reservados + ")");
            }

            // Recalcular disponibles con base en nuevos valores
            int nuevosDisponibles = nuevoTotal - reservados;

            // Setear campos nuevos + heredados
            existente.setEspacio_total(nuevoTotal);
            existente.setDescripcion(espacio.getDescripcion());
            existente.setStatus(espacio.getStatus());
            existente.setDisponibles(nuevosDisponibles);

            return espacioTotalRepository.save(existente);
        } else {
            // Nuevo espacio: disponibles = total
            espacio.setDisponibles(espacio.getEspacio_total());
            return espacioTotalRepository.save(espacio);
        }
    }

    @Override
    @Transactional
    public void delete(Long id){
        EspacioTotal espacio = espacioTotalRepository.findById(id).orElse(null);
        if (espacio != null) {
            espacio.setStatus(false);
            espacio.setDeleted_At(new Date());
            espacioTotalRepository.save(espacio);
        }
    }

    public EspacioTotal actualizarDisponibilidad(Long id, int cambio) {
        EspacioTotal espacio = espacioTotalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado"));

        // Si disponibles es null, lo inicializamos
        if (espacio.getDisponibles() == null) {
            espacio.setDisponibles(espacio.getEspacio_total());
        }

        int disponiblesActuales = espacio.getDisponibles();
        int nuevosDisponibles = disponiblesActuales + cambio;

        // Validamos que no se exceda el total ni baje de 0
        nuevosDisponibles = Math.max(0, Math.min(nuevosDisponibles, espacio.getEspacio_total()));

        espacio.setDisponibles(nuevosDisponibles);

        return espacioTotalRepository.save(espacio);
    }
}
