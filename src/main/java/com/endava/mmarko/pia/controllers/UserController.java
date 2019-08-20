package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.Tour;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.DepartureService;
import com.endava.mmarko.pia.services.TourService;
import com.endava.mmarko.pia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private TourService tourService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> users(){
        return userService.findAll();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User user(@PathVariable String username){
        User user = userService.find(username);
        if(user==null) throw new UserNotFoundError();
        user.setPassword("");
        return user;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String username){
        userService.delete(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody User user){
        User created = userService.save(user);
        if(created==null) throw new CreationConflictError();
        return created;
    }

    @RequestMapping(value = "/{username}/departures", method = RequestMethod.GET)
    public List<Departure> departuresByGuide(@PathVariable String username){
        return departureService.findByGuide(username);
    }

    @RequestMapping(value = "/{username}/tours", method = RequestMethod.GET)
    public List<Tour> toursByGuide(@PathVariable String username){
        return tourService.findByGuide(username);
    }
}
