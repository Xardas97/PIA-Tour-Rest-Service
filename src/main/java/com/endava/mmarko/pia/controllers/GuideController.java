package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Guide;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.services.DepartureService;
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
@RequestMapping("/guides")
public class GuideController {
    private final GuideService guideService;
    private final DepartureService departureService;
    private final TourService tourService;

    @Autowired
    public GuideController(GuideService guideService, DepartureService departureService,
                           TourService tourService){
        this.guideService = guideService;
        this.departureService = departureService;
        this.tourService = tourService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Guide guide(@PathVariable int id){
        Guide guide = guideService.find(id);
        if(guide == null) {
            throw new ResourceNotFoundError("No such Guide exists.");
        }
        return guide;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Guide> guides(){
        return guideService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Guide update(@RequestBody Guide guide, @PathVariable int id){
        guide.setId(id);
        return guideService.save(guide);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        guideService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Guide> save(@RequestBody Guide guide, UriComponentsBuilder ucb){
        Guide created = guideService.save(guide);
        if(created == null) {
            throw new CreationConflictError();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/guides/").
                path(Integer.toString(created.getId())).
                build().toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{guideId}/departures", method = RequestMethod.GET)
    public List<Departure> departuresByGuide(@PathVariable int guideId){
        return departureService.findByGuide(guideId);
    }

    @RequestMapping(value = "/{guideId}/tours", method = RequestMethod.GET)
    public List<Tour> toursByGuide(@PathVariable int guideId){
        return tourService.findByGuide(guideId);
    }
}
