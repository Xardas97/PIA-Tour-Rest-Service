package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, Integer> {
    @Autowired
    public UserController(UserService userService){
        setService(userService);
    }

    public User find(@PathVariable Integer id) {
        try {
            return super.find(id);
        } catch (ResourceNotFoundError e) {
            throw new UserNotFoundError();
        }
    }
}
