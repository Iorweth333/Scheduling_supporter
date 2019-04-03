package ioiobagiety.service;

import ioiobagiety.model.HelloIOBagiety;
import ioiobagiety.exception.BadRequestException;
import ioiobagiety.exception.ResourceNotFoundException;

import java.util.List;

public interface HelloIOBagietyService {

    HelloIOBagiety get(Long id) throws ResourceNotFoundException;

    List<HelloIOBagiety> getAll() throws ResourceNotFoundException;

    HelloIOBagiety create(HelloIOBagiety helloIOBagiety) throws BadRequestException;

    HelloIOBagiety getCustom();
}

