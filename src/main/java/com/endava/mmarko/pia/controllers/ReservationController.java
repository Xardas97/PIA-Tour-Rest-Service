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
@RequestMapping("/users/{userId}/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Reservation> reservations(@PathVariable int userId){
        return reservationService.findByClient(userId);
    }

    @RequestMapping(value = "/{resId}",method = RequestMethod.GET)
    public Reservation reservation(@PathVariable int userId, @PathVariable int resId){
        Reservation r = reservationService.find(userId, resId);
        if(r==null) {
            throw new ResourceNotFoundError("Reservation with that ID doesn't exist or doesn't belong to that user");
        }
        return r;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Reservation> save(@RequestBody Reservation r, UriComponentsBuilder ucb){
        Reservation created = reservationService.save(r);
        if(created==null) {
            throw new CreationConflictError();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucb.path("//users/").
                path(Integer.toString(created.getClient().getId())).
                path("/reservations/").
                path(Integer.toString(created.getId())).
                build().toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{resId}", method = RequestMethod.PUT)
    public Reservation update(@RequestBody Reservation res, @PathVariable int resId){
        res.setId(resId);
        return reservationService.save(res);
    }

    @RequestMapping(value = "/{resId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer userId, @PathVariable int resId) {
        reservationService.delete(userId, resId);
    }
}
