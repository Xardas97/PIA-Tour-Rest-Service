package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.models.Departure;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.DepartureService;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<User> users(){
        return userService.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User user(@PathVariable int userId){
        User user = userService.find(userId);
        if(user==null) {
            throw new UserNotFoundError();
        }
        user.setPassword("");
        return user;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int userId){
        userService.delete(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody User user){
        User created = userService.save(user);
        if(created==null) {
            throw new CreationConflictError();
        }
        return created;
    }

    @RequestMapping(value = "/{guideId}/departures", method = RequestMethod.GET)
    public List<Departure> departuresByGuide(@PathVariable int guideId){
        return departureService.findByGuide(guideId);
    }
}
