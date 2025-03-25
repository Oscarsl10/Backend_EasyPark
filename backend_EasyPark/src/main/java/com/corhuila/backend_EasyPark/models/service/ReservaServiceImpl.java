package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Reserva;
import com.corhuila.backend_EasyPark.models.repository.IReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService{

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll(){
        return (List<Reserva>) reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva findById(Long id){
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void delete(Long id){
        reservaRepository.deleteById(id);
    }
}
