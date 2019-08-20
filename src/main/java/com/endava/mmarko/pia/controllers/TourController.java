package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Tour;
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
public class TourController {
   @Autowired
    private TourService tourService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Tour tour(@PathVariable int id){
        Tour tour = tourService.find(id);
        if(tour == null) throw new ResourceNotFoundError("No such Tour exists.");
        return tour;
    }

   @RequestMapping(method = RequestMethod.GET)
    public List<Tour> tours(){
       return tourService.findAll();
   }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Tour update(@RequestBody Tour tour, @PathVariable int id){
        tour.setId(id);
        return tourService.save(tour);
    }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   public void delete(@PathVariable int id){
        tourService.delete(id);
   }

   @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Tour> save(@RequestBody Tour tour, UriComponentsBuilder ucb){
       Tour created = tourService.save(tour);
       if(created == null) throw new CreationConflictError();

       HttpHeaders headers = new HttpHeaders();
       headers.setLocation(ucb.path("/tours/").
               path(Integer.toString(created.getId())).
               build().toUri());

       return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
   }
}
