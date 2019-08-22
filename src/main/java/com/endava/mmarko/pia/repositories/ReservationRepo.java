package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.client.id = ?1")
    List<Reservation> findByClient(int client);
    Reservation findByDepartureAndClient(Departure departure, User client);
    int countByDeparture(Departure departure);
    @Query("DELETE FROM Reservation r WHERE r.client.id = ?1 AND r.id=?2")
    void deleteByClientAndId(int client, int id);
}
