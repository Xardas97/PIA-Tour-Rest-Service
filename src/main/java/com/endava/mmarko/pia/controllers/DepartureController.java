package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.services.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/departures")
public class DepartureController {
    private final DepartureService departureService;

    @Autowired
    public DepartureController(DepartureService departureService){
        this.departureService = departureService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Departure> departures(){
        return departureService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Departure departure(@PathVariable int id) {
        Departure departure = departureService.find(id);
        if(departure == null) {
            throw new ResourceNotFoundError("No such Departure exists");
        }
        return departure;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Departure update(@RequestBody Departure dep, @PathVariable int id){
        dep.setId(id);
        return departureService.update(dep);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        departureService.delete(id);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Departure> save(@RequestBody Departure d, UriComponentsBuilder ucb){
        Departure created = departureService.save(d);
        if(created==null) {
            throw new CreationConflictError("Departure Already Exists");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("/departures/").
                                path(Integer.toString(created.getId())).
                                build().toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/has_enough_people", method = RequestMethod.GET)
    public boolean hasEnoughPeople(@PathVariable int id){
        return departureService.hasEnoughPeople(id);
    }
}