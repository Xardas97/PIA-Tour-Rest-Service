package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepo extends JpaRepository<Tour, Integer> {
    List<Tour> findByMyGuides_id(int guide);
}
