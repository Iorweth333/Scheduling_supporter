package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSFileExportProvider {
    public static final String FILENAME = "schedule.xls";
    public static final String PATH = "uploads/" + FILENAME;
    private static String[] columns = {"MeetingNumber", "Date", "Hours", "Group", "Subject", "Classroom", "Lecturer"};

    public static void provideFile(List<Lesson> lessons) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Schedule");
        createHeader(workbook, sheet);
        fillSchedule(workbook, sheet, lessons);
        createFileFromWorkbook(workbook);
    }

    private static void createFileFromWorkbook(Workbook workbook) throws IOException {
        File file = new File(PATH);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
    }


    private static void createHeader(Workbook workbook, Sheet sheet) {
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 14);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private static void fillSchedule(Workbook workbook, Sheet sheet, List<Lesson> lessons) {
        CellStyle dateCellStyle = getDataCellStyle(workbook);
        int rowNum = 1;
        for (Lesson lesson : lessons) {
            Row row = sheet.createRow(rowNum++);
            fillRow(lesson, row, dateCellStyle);
        }
    }

    private static void fillRow(Lesson lesson, Row row, CellStyle dateCellStyle) {
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

    private static CellStyle getDataCellStyle(Workbook workbook) {
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));
        return dateCellStyle;
    }
}
