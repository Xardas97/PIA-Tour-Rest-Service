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
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> users(){
        List<User> users = userService.findAll();
        users.forEach(user -> user.setPassword(""));
        return users;
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
}
