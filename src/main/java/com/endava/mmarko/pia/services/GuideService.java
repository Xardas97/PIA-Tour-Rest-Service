package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.repositories.GuideRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuideService extends AbstractService<Guide, Integer> {

  @Autowired
  public GuideService(GuideRepo guideRepo) {
    setRepo(guideRepo);
  }

  public List<Guide> findByTour(Integer tour) {
    return getRepo().findByMyTours_id(tour);
  }

  @Override
  GuideRepo getRepo() {
    return (GuideRepo) super.getRepo();
  }
}
