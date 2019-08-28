package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.ErrorReturnResponse;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public User login(@RequestBody Map<String, String> wrapper) {
        return userService.findByUsernameAndPassword(wrapper.get("username"), wrapper.get("password"));
    }

    @ExceptionHandler(WrongPasswordError.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorReturnResponse wrongPasswordError(){
        return new ErrorReturnResponse(2, "Wrong Password");
    }
}
