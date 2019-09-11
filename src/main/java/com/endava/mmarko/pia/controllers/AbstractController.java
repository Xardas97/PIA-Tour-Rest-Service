package com.endava.mmarko.pia.controllers;

import com.endava.mmarko.pia.errors.CreationConflictError;
import com.endava.mmarko.pia.errors.ResourceNotFoundError;
import com.endava.mmarko.pia.models.AbstractModel;
import com.endava.mmarko.pia.services.AbstractService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public abstract class AbstractController<T extends AbstractModel<ID>, ID> {
    private AbstractService<T, ID> service;

    @RequestMapping(method = RequestMethod.GET)
    public List<T> findAll(){
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T find(@PathVariable ID id) {
        T obj = service.find(id);
        if(obj == null) {
            throw new ResourceNotFoundError("No such resource exists");
        }
        return obj;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public T update(@RequestBody T obj, @PathVariable ID id) {
        obj.setId(id);
        return service.update(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<T> save(@RequestBody T d){
        T created = service.save(d);
        if(created==null) {
            throw new CreationConflictError("Resource Already Exists");
        }

        HttpHeaders headers = new HttpHeaders();

        ID id = created.getId();
        if (id instanceof Integer || id instanceof String) {
            headers.setLocation(
                    ServletUriComponentsBuilder
                            .fromCurrentRequestUri()
                            .path("{id}")
                            .buildAndExpand(id)
                            .toUri()
            );
        }

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    void setService(AbstractService<T, ID> service) {
        this.service = service;
    }

    protected AbstractService<T, ID> getService() {
        return service;
    }
}
