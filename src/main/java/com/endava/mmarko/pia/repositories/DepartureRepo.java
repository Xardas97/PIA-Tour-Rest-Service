package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DepartureRepo extends JpaRepository<Departure, Integer> {
    Departure findByGuideAndDate(TourGuide guide, Date date);
    @Query("SELECT d FROM Departure d, TourGuide t WHERE d.guide=t AND t.guide=?1")
    List<Departure> findByGuide(String guide);
}
