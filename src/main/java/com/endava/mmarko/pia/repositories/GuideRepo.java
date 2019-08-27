package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepo extends JpaRepository<Guide, Integer> {
    List<Guide> findByMyTours_id(int tour);
}
