package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Tour;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepo extends JpaRepository<Tour, Integer> {
  List<Tour> findByMyGuides_id(int guide);
}
