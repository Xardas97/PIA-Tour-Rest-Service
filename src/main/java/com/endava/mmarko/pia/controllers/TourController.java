package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.services.GuideService;
import com.endava.mmarko.pia.services.TourService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tours")
public class TourController extends AbstractController<Tour, Integer> {
  private final GuideService guideService;

  @Autowired
  public TourController(TourService tourService, GuideService guideService) {
    setService(tourService);
    this.guideService = guideService;
  }

  @RequestMapping(value = "/{tourId}/guides", method = RequestMethod.GET)
  public List<Guide> guidesByTour(@PathVariable int tourId) {
    return guideService.findByTour(tourId);
  }
}
