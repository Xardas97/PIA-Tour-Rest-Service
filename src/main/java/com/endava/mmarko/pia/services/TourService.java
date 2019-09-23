package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.repositories.TourRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourService extends AbstractService<Tour, Integer> {

  @Autowired
  public TourService(TourRepo tourRepo) {
    setRepo(tourRepo);
  }

  public List<Tour> findByGuide(int guideId) {
    return getRepo().findByMyGuides_id(guideId);
  }

  @Override
  TourRepo getRepo() {
    return (TourRepo) super.getRepo();
  }
}
