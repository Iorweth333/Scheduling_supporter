package ioiobagiety.controller;

import ioiobagiety.model.HelloIOBagiety;
import ioiobagiety.service.HelloIOBagietyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import java.util.List;

@RestController
@RequestMapping(value = "/bagieta")
public class HelloIOBagietyController {

    @Autowired
    private HelloIOBagietyService helloIOBagietyService;

    @Autowired
    private Gson gson;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getHelloIOBagiety() {

        List<HelloIOBagiety> helloIOBagieties = helloIOBagietyService.getAll();

        if (helloIOBagieties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(helloIOBagieties), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getForum(@PathVariable("id") Long id) {
        HelloIOBagiety helloIOBagiety = helloIOBagietyService.get(id);
        if (helloIOBagiety == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(helloIOBagiety), HttpStatus.OK);
        }
    }
}