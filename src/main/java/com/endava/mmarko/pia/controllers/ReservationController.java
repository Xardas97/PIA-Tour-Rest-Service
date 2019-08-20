package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.Reservation;
import com.endava.mmarko.pia.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users/{username}/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Reservation> reservations(@PathVariable String username){
        return reservationService.findByClient(username);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Reservation reservation(@PathVariable String username, @PathVariable int id){
        Reservation r = reservationService.find(username, id);
        if(r==null) throw new ResourceNotFoundError("Reservation with that ID doesn't exist or doesn't belong to that user");
        return r;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Reservation> save(@RequestBody Reservation r, UriComponentsBuilder ucb){
        Reservation created = reservationService.save(r);
        if(created==null) throw new CreationConflictError();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("//users/").
                path(created.getClient()).
                path("/reservations/").
                path(Integer.toString(created.getId())).
                build().toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Reservation update(@RequestBody Reservation res, @PathVariable int id){
        res.setId(id);
        return reservationService.save(res);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String username, @PathVariable int id) {
        reservationService.delete(username, id);
    }
}
