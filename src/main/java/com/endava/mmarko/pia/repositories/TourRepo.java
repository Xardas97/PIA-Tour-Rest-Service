package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepo extends JpaRepository<Tour, Integer> {
    @Query("SELECT t FROM Tour t, TourGuide tg WHERE tg.tour=t AND tg.guide=?1")
    List<Tour> findByGuide(String guide);
}
