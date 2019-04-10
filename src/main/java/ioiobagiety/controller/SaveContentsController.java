package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import ioiobagiety.util.CsvParser;
import ioiobagiety.util.XLSXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class SaveContentsController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private LessonService lessonService;

    public void saveFileContents(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
                saveXLSXContents(file);
            }
            else if(filename.endsWith(".csv")){
                saveCsvContents(file);
            }
        }
    }

    public void saveXLSXContents (MultipartFile file) {
        try {
            XLSXParser parser = new XLSXParser();

            ArrayList<Lesson> lessons = parser.parseXLSX(file);

            lessons.forEach(lessonService::create);

        } catch (Exception ex) {
            logger.info("Could not import from excel file. " + ex.getMessage());
        }
    }

    public void saveCsvContents (MultipartFile file) {
        try {
            CsvParser parser = new CsvParser();

            ArrayList<Lesson> lessons = parser.parseCsv(file);

            lessons.forEach(lessonService::create);

        } catch (Exception ex) {
            logger.info("Could not import from csv file. " + ex.getMessage());
        }
    }
}
