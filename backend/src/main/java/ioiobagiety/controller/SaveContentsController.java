package ioiobagiety.controller;

import ioiobagiety.exception.XLSXParseException;
import ioiobagiety.service.LessonService;
import ioiobagiety.util.XLSXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class SaveContentsController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private LessonService lessonService;

    public void saveFileContents (MultipartFile file) {
        try {
            XLSXParser parser = new XLSXParser(file);
            parser.getLessons().forEach(lessonService::create);
        } catch (XLSXParseException e) {
            logger.info(e.getMessage());
        }
    }
}