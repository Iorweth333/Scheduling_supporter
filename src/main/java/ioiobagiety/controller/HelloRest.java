package ioiobagiety.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HelloRest {

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "Hello rest io io io";
    }
}