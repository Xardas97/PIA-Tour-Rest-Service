package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAutoRepo extends JpaRepository<User, String> {
    List<User> findByFirstNameAndLastName(String first, String last);
}
