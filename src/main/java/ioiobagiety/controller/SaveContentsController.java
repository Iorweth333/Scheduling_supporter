package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.service.LessonService;
import ioiobagiety.util.XLSXParser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class SaveContentsController {

    @Autowired
    private LessonService lessonService;

    public void saveXLSXContents (MultipartFile file) throws MultipartException {
        try {
            XSSFWorkbook offices = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = offices.getSheetAt(0);

            XLSXParser parser = new XLSXParser();

            ArrayList<Lesson> lessons = parser.parseSheet(worksheet);

            lessons.forEach(lessonService::create);

        } catch (Exception e) {
            throw new MultipartException("Constraints Violated");
        }
    }
}
