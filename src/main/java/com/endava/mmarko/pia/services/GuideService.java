package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.repositories.GuideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuideService extends AbstractService<Guide, Integer> {

    @Autowired
    public GuideService(GuideRepo guideRepo) {
        setRepo(guideRepo);
    }

    public List<Guide> findByTour(Integer tour){
        return getRepo().findByMyTours_id(tour);
    }

    @Override
    protected GuideRepo getRepo() {
        return (GuideRepo) super.getRepo();
    }
}
