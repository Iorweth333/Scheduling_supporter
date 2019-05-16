package ioiobagiety.controller;

import com.google.gson.Gson;
import ioiobagiety.response.Conflict;
import ioiobagiety.service.ConflictsFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
public class ConflictsFinderController {

    @Autowired
    private ConflictsFinderService conflictsFinderService;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/conflicts", method = RequestMethod.GET)
    public ResponseEntity<String> ConflictsList() {

        List<Conflict> conflicts = conflictsFinderService.getAllConflicts();

        if (conflicts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(conflicts), HttpStatus.OK);
        }
    }
}