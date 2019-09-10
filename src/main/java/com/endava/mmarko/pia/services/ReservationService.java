package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationService extends AbstractService<Reservation, Integer> {

    @Autowired
    public ReservationService(ReservationRepo reservationRepo) {
        setRepo(reservationRepo);
    }

    public Reservation find(Integer userId, Integer reservationId){
        Reservation res = find(reservationId);
        if(res != null && res.getClient().getId() == userId){
            return res;
        }
        return null;
    }

    public void delete(Integer userId, Integer reservationId){
        getRepo().deleteByClientAndId(userId, reservationId);
    }

    public List<Reservation> findByClient(Integer client){
        return getRepo().findByClient(client);
    }

    @Override
    public Reservation save(Reservation r) {
        Reservation byDepartureAndClient = getRepo().findByDepartureAndClient(r.getDeparture(), r.getClient());
        if (byDepartureAndClient != null) {
            return null;
        }
        return super.save(r);
    }

    @Override
    protected ReservationRepo getRepo() {
        return (ReservationRepo) super.getRepo();
    }
}
