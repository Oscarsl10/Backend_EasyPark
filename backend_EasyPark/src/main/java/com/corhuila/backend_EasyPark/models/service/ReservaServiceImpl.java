package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.repository.IReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService{

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll(){
        return reservaRepository.findByStatusTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva findById(Long id){
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        return (reserva != null && Boolean.TRUE.equals(reserva.getStatus())) ? reserva : null;
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setStatus(false);
            reserva.setDeleted_At(new Date());
            reservaRepository.save(reserva);
        }
    }
}
