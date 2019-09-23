package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.services.DepartureService;
import com.endava.mmarko.pia.services.GuideService;
import com.endava.mmarko.pia.services.TourService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guides")
public class GuideController extends AbstractController<Guide, Integer> {
  private final DepartureService departureService;
  private final TourService tourService;

  @Autowired
  public GuideController(GuideService guideService,
                         DepartureService departureService,
                         TourService tourService) {
    setService(guideService);
    this.departureService = departureService;
    this.tourService = tourService;
  }

  @RequestMapping(value = "/{guideId}/departures", method = RequestMethod.GET)
  public List<Departure> departuresByGuide(@PathVariable int guideId) {
    return departureService.findByGuide(guideId);
  }

  @RequestMapping(value = "/{guideId}/tours", method = RequestMethod.GET)
  public List<Tour> toursByGuide(@PathVariable int guideId) {
    return tourService.findByGuide(guideId);
  }
}
