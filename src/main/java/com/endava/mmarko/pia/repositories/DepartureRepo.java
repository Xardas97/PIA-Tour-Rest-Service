package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartureRepo extends JpaRepository<Departure, Integer> {
  Departure findByGuideAndDate(Guide guide, Date date);

  @Query("SELECT d FROM Departure d WHERE d.guide.id = ?1")
  List<Departure> findByGuide(int guide);
}
