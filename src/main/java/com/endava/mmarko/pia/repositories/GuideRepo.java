package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Guide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepo extends JpaRepository<Guide, Integer> {
  List<Guide> findByMyTours_id(int tour);
}
