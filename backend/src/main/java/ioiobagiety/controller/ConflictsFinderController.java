package ioiobagiety.controller;

import ioiobagiety.exception.XLSXParseException;
import ioiobagiety.service.LessonService;
import ioiobagiety.util.XLSXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/conflicts")
public class ConflictsFinderController {

    private static final Logger logger = LoggerFactory.getLogger(ConflictsFinderController.class);

    @Autowired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> ConflictsList() {

        List<Conflicts> conflicts = ConflictsFinderService.getAll();

        if (conflicts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gson.toJson(conflicts), HttpStatus.OK);
        }
    }
}