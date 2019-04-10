package ioiobagiety.service.impl;

import ioiobagiety.model.HelloIOBagiety;
import ioiobagiety.repository.HelloIOBagietyRepository;
import ioiobagiety.exception.ResourceNotFoundException;
import ioiobagiety.service.HelloIOBagietyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HelloIOBagietyServiceImpl implements HelloIOBagietyService {

    @Autowired
    private HelloIOBagietyRepository helloIOBagietyRepository;

    @Transactional
    public HelloIOBagiety get(Long id) {
        HelloIOBagiety helloIOBagiety = helloIOBagietyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HelloIOBagiety"));
        return helloIOBagiety;
    }

    @Transactional
    public List<HelloIOBagiety> getAll() {
        List<HelloIOBagiety> helloIOBagieties = helloIOBagietyRepository.findAll();
        if (helloIOBagieties.size() > 0) {
            return helloIOBagieties;
        } else {
            throw new ResourceNotFoundException("HelloIOBagiety");
        }
    }

    @Transactional
    public HelloIOBagiety create(HelloIOBagiety helloIOBagiety) {
        return helloIOBagietyRepository.save(helloIOBagiety);
    }

    @Transactional
    public HelloIOBagiety getCustom() {
        return helloIOBagietyRepository.getByCustomQuery().get();
    }
}