package com.endava.mmarko.pia.services;

import com.endava.mmarko.pia.errors.UserNotFoundError;
import com.endava.mmarko.pia.errors.WrongPasswordError;
import com.endava.mmarko.pia.models.User;
import com.endava.mmarko.pia.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService extends AbstractService<User, Integer> {

    @Autowired
    public UserService(UserRepo userRepo) {
        setRepo(userRepo);
    }

    public User findByUsernameAndPassword(String username, String password)
            throws UserNotFoundError, WrongPasswordError {
        User user = getRepo().findByUsername(username);
        if(user == null) {
            throw new UserNotFoundError();
        }
        if(!user.getPassword().equals(password)) {
            throw new WrongPasswordError();
        }
        return user;
    }

    @Override
    protected UserRepo getRepo() {
        return (UserRepo) super.getRepo();
    }
}
