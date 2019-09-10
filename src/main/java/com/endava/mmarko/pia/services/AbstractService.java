package com.endava.mmarko.pia.services;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractService<T, ID> {
    private JpaRepository<T, ID> repo;

    public List<T> findAll() {
        return repo.findAll();
    }

    public T find(ID id) {
        return repo.findById(id).orElse(null);
    }

    public T save(T t) {
        return repo.save(t);
    }

    public T update(T t) {
        return repo.save(t);
    }

    public void delete(ID id) {
        repo.deleteById(id);
    }

    void setRepo(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    protected JpaRepository<T, ID> getRepo() {
        return repo;
    }
}
