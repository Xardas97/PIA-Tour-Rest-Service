package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, Integer> {
    @Autowired
    public UserController(UserService userService){
        setService(userService);
    }
}
