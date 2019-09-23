package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
  User findByUsername(String username);
}
