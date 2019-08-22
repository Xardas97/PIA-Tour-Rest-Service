package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.repositories.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TourService {
    @Autowired
    private TourRepo tourRepo;

    public Tour save(Tour tour){
        return tourRepo.save(tour);
    }

    public Tour find(Integer id){
        return tourRepo.findOne(id);
    }

    public List<Tour> findAll() { return tourRepo.findAll(); }

    public void delete(Integer id) {
        tourRepo.delete(id);
    }
}
