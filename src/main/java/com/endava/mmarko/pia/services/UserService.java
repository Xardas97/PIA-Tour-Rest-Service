package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User find(Integer id){
        return userRepo.findOne(id);
    }

    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundError, WrongPasswordError {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundError();
        }
        if(!user.getPassword().equals(password)) {
            throw new WrongPasswordError();
        }
        return user;
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public void delete(Integer id){
        userRepo.delete(id);
    }
}
