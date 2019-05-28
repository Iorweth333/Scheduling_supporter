package ioiobagiety.controller;

import ioiobagiety.service.FileStorageService;
import ioiobagiety.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/schedule/xls")
public class XLSFileExportController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) throws IOException {
        XLSFileExportProvider.provideFile(lessonService.getAll());
        return getResourceResponseEntity(request);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String name, HttpServletRequest request) throws IOException {
        XLSFileExportProvider.provideFile(lessonService.getLessonsFromScheduleName(name));
        return getResourceResponseEntity(request);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(XLSFileExportProvider.FILENAME);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/group/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadScheduleForGroup(@PathVariable String name, HttpServletRequest request) throws IOException {
        XLSFileExportProvider.provideFile(lessonService.getLessonsFromGroupName(name));
        return getResourceResponseEntity(request);
    }
}



