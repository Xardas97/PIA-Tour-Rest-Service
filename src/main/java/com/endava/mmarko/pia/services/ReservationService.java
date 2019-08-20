package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationService {
    @Autowired
    private ReservationRepo reservationRepo;

    public Reservation find(String username, Integer id){
        Reservation r = reservationRepo.findOne(id);
        if(r.getClient().equals(username)) return r;
        return null;
    }

    public void delete(String username, Integer id){
        reservationRepo.deleteByClientAndId(username, id);
    }

    public List<Reservation> findByClient(String client){
        return reservationRepo.findByClient(client);
    }

    public Reservation save(Reservation r){
        if(reservationRepo.findByDepartureAndClient(r.getDeparture(), r.getClient())!=null) return null;
        return reservationRepo.save(r);
    }
}
