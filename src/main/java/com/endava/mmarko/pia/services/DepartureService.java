package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.repositories.DepartureRepo;
import com.endava.mmarko.pia.repositories.ReservationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartureService extends AbstractService<Departure, Integer> {

  private final ReservationRepo reservationRepo;

  @Autowired
  public DepartureService(ReservationRepo reservationRepo, DepartureRepo departureRepo) {
    this.reservationRepo = reservationRepo;
    setRepo(departureRepo);
  }

  @Override
  public Departure save(Departure d) {
    if (getRepo().findByGuideAndDate(d.getGuide(), d.getDate()) != null) {
      return null;
    }
    return super.save(d);
  }

  public Boolean hasEnoughPeople(Integer id) throws ResourceNotFoundError {
    Departure departure = find(id);
    if (departure == null) {
      throw new ResourceNotFoundError();
    }
    int minPeople = departure.getTour().getMinPeople();
    int reserved = reservationRepo.countByDeparture(departure);
    return reserved >= minPeople;
  }

  public List<Departure> findByGuide(Integer guideId) {
    return getRepo().findByGuide(guideId);
  }

  @Override
  DepartureRepo getRepo() {
    return (DepartureRepo) super.getRepo();
  }
}
