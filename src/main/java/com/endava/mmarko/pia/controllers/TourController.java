package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.services.GuideService;
import com.endava.mmarko.pia.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController extends AbstractController<Tour, Integer> {
    private final GuideService guideService;

    @Autowired
    public TourController(TourService tourService, GuideService guideService){
       setService(tourService);
       this.guideService = guideService;
   }

    @RequestMapping(value = "/{tourId}/guides", method = RequestMethod.GET)
    public List<Guide> guidesByTour(@PathVariable int tourId){
        return guideService.findByTour(tourId);
    }
}
