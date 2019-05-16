package ioiobagiety.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.user.AppUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StudentsListServiceImpl {

    String getStudentsList(Lesson lesson) {

        try {
            String fpath = "/temp/sample.pdf";

            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }

            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));

            document.open();
            document.add(new Paragraph(lesson.getSubject().getName() + " Grupa: " + lesson.getStudentsGroup().getName()))
            document.add(new Paragraph(lesson.getDate + " " + lesson.getStartsAt() + " " + lesson.getEndsAt()));
            document.add(new Paragraph(" "));

            int num = 1;
            for (AppUser student : lesson.getStudentsGroup().getStudents()) {
                document.add(new Paragraph(num + ". " + student.getName() + " " + student.getSurname()));
                num++;
            }

            document.close();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (DocumentException e) {
            e.printStackTrace();
            return "";
        }
    }
}
