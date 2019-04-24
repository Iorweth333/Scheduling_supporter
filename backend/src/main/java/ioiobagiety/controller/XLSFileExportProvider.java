package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.List;

public class XLSFileExportProvider {
    private static String[] columns;

    public static File provideFile(List<Lesson> lessons) {
        Workbook workbook = new HSSFWorkbook();
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
        return null;

    }
}
