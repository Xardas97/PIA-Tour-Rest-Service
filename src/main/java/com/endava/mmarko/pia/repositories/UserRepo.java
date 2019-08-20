package com.endava.mmarko.pia.repositories;

import com.endava.mmarko.pia.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(User user) {
        entityManager.persist(user);
    }

    public User getByUsername(String username){
        return entityManager.find(User.class, username);
    }

    public void save(User user){
        entityManager.merge(user);
    }

    public void removeByUsername(String username){
        User user = getByUsername(username);
        if(user!=null)
        entityManager.remove(user);
    }

    public List<User> getAll(){
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }
}
