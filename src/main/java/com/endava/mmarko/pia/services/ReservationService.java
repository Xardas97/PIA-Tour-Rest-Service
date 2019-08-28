package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationService {
    @Autowired
    private ReservationRepo reservationRepo;

    public Reservation find(Integer userId, Integer reservationId){
        Optional<Reservation> r = reservationRepo.findById(reservationId);
        if(r.isPresent() && r.get().getClient().getId() == userId){
            return r.get();
        }
        return null;
    }

    public void delete(Integer userId, Integer reservationId){
        reservationRepo.deleteByClientAndId(userId, reservationId);
    }

    public List<Reservation> findByClient(Integer client){
        return reservationRepo.findByClient(client);
    }

    public Reservation save(Reservation r) {
        Reservation byDepartureAndClient = reservationRepo.findByDepartureAndClient(r.getDeparture(), r.getClient());
        if (byDepartureAndClient != null) {
            return null;
        }
        return reservationRepo.save(r);
    }
}
