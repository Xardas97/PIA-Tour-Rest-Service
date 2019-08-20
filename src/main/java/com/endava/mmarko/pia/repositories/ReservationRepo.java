package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByClient(String client);
    Reservation findByDepartureAndClient(Departure departure, String client);
    int countByDeparture(Departure departure);
    void deleteByClientAndId(String client, int id);
}
