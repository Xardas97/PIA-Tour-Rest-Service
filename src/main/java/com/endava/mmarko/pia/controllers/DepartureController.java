package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.services.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departures")
public class DepartureController extends AbstractController<Departure, Integer> {
    @Autowired
    public DepartureController(DepartureService departureService){
        setService(departureService);
    }

    @RequestMapping(value = "/{id}/has_enough_people", method = RequestMethod.GET)
    public boolean hasEnoughPeople(@PathVariable int id){
        return getService().hasEnoughPeople(id);
    }

    @Override
    protected DepartureService getService() {
        return (DepartureService) super.getService();
    }
}