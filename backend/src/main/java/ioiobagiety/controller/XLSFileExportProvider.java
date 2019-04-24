package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSFileExportProvider {
    private static String[] columns = {"MeetingNumber", "Date", "Hours", "Group", "Subject", "Classroom", "Lecturer"};

    public static File provideFile(List<Lesson> lessons) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Schedule");
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        int rowNum = 1;
        for (Lesson lesson : lessons) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(lesson.getMeetingNumber());
            Cell dateCell = row.createCell(1);
            dateCell.setCellValue(lesson.getDate());
            dateCell.setCellStyle(dateCellStyle);
            row.createCell(2).setCellValue(lesson.getStartsAt() + "-" + lesson.getEndsAt());
            row.createCell(3).setCellValue(lesson.getStudentsGroup().getName());
            row.createCell(4).setCellValue(lesson.getSubject().getName());
            row.createCell(5).setCellValue(lesson.getClassroom().getNumber());
            row.createCell(6).setCellValue(lesson.getLecturer().getName() + lesson.getLecturer().getSurname());
        }
        File file = new File("schedule.xls");
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        return file;
    }
}
