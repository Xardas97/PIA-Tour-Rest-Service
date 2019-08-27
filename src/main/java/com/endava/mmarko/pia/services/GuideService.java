package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.repositories.GuideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuideService {
    @Autowired
    private GuideRepo guideRepo;

    public Guide save(Guide guide){
        return guideRepo.save(guide);
    }

    public Guide find(Integer id){
        return guideRepo.findOne(id);
    }

    public List<Guide> findAll() {
        return guideRepo.findAll();
    }

    public void delete(Integer id) {
        guideRepo.delete(id);
    }

    public List<Guide> findByTour(Integer tour){
        return guideRepo.findByMyTours_id(tour);
    }
}
