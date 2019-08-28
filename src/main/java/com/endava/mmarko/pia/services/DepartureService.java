package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.repositories.DepartureRepo;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartureService {
    @Autowired
    private DepartureRepo departureRepo;
    @Autowired
    private ReservationRepo reservationRepo;

    public Departure find(Integer id){
        return departureRepo.findById(id).orElse(null);
    }

    public List<Departure> findAll(){
        return departureRepo.findAll();
    }

    public Departure save(Departure d){
        if (departureRepo.findByGuideAndDate(d.getGuide(), d.getDate()) != null) {
            return null;
        }
        return departureRepo.save(d);
    }

    public Departure update(Departure d){
        return departureRepo.save(d);
    }

    public void delete(Integer id){
        departureRepo.deleteById(id);
    }

    public Boolean hasEnoughPeople(Integer id) throws ResourceNotFoundError {
        Departure departure = departureRepo.findById(id).orElse(null);
        if (departure == null) {
            throw new ResourceNotFoundError();
        }
        int minPeople = departure.getTour().getMinPeople();
        int reserved = reservationRepo.countByDeparture(departure);
        return reserved >= minPeople;
    }

    public List<Departure> findByGuide(Integer guideId){
        return departureRepo.findByGuide(guideId);
    }
}
