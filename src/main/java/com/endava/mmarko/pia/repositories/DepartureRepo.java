package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DepartureRepo extends JpaRepository<Departure, Integer> {
    Departure findByGuideAndDate(Guide guide, Date date);
    @Query("SELECT d FROM Departure d WHERE d.guide.id = ?1")
    List<Departure> findByGuide(int guide);
}
