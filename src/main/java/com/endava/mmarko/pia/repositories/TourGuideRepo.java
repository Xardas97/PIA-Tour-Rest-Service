package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourGuideRepo extends JpaRepository<TourGuide, Integer> {
    @Query("SELECT DISTINCT guide FROM TourGuide")
    List<TourGuide> findAllGuides();
}
