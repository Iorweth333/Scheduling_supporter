package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/schedule/file")
public class XLSFileExportController {

    @Autowired
    private LessonService lessonService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource> getLessons() throws IOException {
        List<Lesson> lessons = lessonService.getAll();
        File file = XLSFileExportProvider.provideFile(lessons);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}


