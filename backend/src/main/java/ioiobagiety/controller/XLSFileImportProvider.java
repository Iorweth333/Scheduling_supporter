package ioiobagiety.controller;

import ioiobagiety.exception.XLSParseException;
import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classes.Subject;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.group.StudentsGroup;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.model.user.UserType;
import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jooq.lambda.Unchecked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
public class XLSFileImportProvider {

    private Map<String, Subject> subjects = new HashMap<>();
    private Map<String, Classroom> classrooms = new HashMap<>();
    private Map<String, AppUser> lecturers = new HashMap<>();
    private Map<String, StudentsGroup> groups = new HashMap<>();
    private List<Lesson> lessons = new LinkedList<>();

    public XLSFileImportProvider(MultipartFile file) throws XLSParseException {
        try {
            Workbook offices = new XSSFWorkbook(file.getInputStream());
            Iterator<Sheet> worksheets = offices.sheetIterator();
            worksheets.forEachRemaining(Unchecked.consumer(this::parseSheet));
        } catch (Exception ex) {
            throw new XLSParseException("Could not parse " + file.getOriginalFilename(), ex);
        }
    }

    private void parseSheet(Sheet worksheet) throws ParseException {

        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();
        rows.forEachRemaining(row -> {
            addGroup(row);
            addSubject(row);
            addClassroom(row);
            addLecturer(row);
        });

        addLessons(worksheet);
    }

    private void addLessons(Sheet worksheet) throws ParseException {
        String scheduleName = worksheet.getSheetName();
        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();

        int meetingNumber = 0;
        while (rows.hasNext()) {
            Row row = rows.next();
            meetingNumber = parseMeetingNumber(row, meetingNumber);
            Lesson lesson = Lesson.builder()
                    .scheduleName(scheduleName)
                    .meetingNumber(meetingNumber)
                    .date(parseDate(row))
                    .startsAt(parseStartTime(row))
                    .endsAt(parseEndTime(row))
                    .studentsGroup(groups.get(parseGroup(row)))
                    .subject(subjects.get(parseSubject(row)))
                    .classroom(classrooms.get(parseClassroom(row)))
                    .lecturer(lecturers.get(parseLecturer(row)))
                    .build();

            lessons.add(lesson);
        }
    }

    private void addSubject(Row row) {
        String name = parseSubject(row);
        Subject subject = Subject.builder()
                .name(name)
                .build();
        subjects.putIfAbsent(name, subject);

    }

    private void addClassroom(Row row) {
        String number = parseClassroom(row);
        Classroom classroom = Classroom.builder()
                .number(number)
                .build();
        classrooms.putIfAbsent(number, classroom);

    }

    private void addLecturer(Row row) {
        String fullName = parseLecturer(row);
        String[] name = fullName.split("\\s+");
        AppUser lecturer = AppUser.builder()
                .userType(UserType.lecturer)
                .name(name[0])
                .surname(name[1])
                .build();
        lecturers.putIfAbsent(fullName, lecturer);
    }

    private void addGroup(Row row) {
        String name = parseGroup(row);
        StudentsGroup group = StudentsGroup.builder()
                .name(name)
                .build();

        groups.putIfAbsent(name, group);


    }

    private int parseMeetingNumber(Row row, int lastNumber) {
        Cell cell = row.getCell(Column.MEETING.ordinal());
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return lastNumber;
    }

    private Date parseDate(Row row) throws ParseException {
        String date = row.getCell(Column.DATE.ordinal()).getStringCellValue();
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    private Time parseStartTime(Row row) throws ParseException {
        String startAt = row.getCell(Column.TIME.ordinal()).getStringCellValue().split("-")[0];
        Date startDate = new SimpleDateFormat("HH:mm").parse(startAt);
        return new Time(startDate.getTime());
    }

    private Time parseEndTime(Row row) throws ParseException {
        String endsAt = row.getCell(Column.TIME.ordinal()).getStringCellValue().split("-")[1];
        Date endDate = new SimpleDateFormat("HH:mm").parse(endsAt);
        return new Time(endDate.getTime());
    }

    private String parseGroup(Row row) {
        return row.getCell(Column.GROUP.ordinal()).getStringCellValue();
    }

    private String parseSubject(Row row) {
        return row.getCell(Column.SUBJECT.ordinal()).getStringCellValue();
    }

    private String parseClassroom(Row row) {
        return row.getCell(Column.CLASSROOM.ordinal()).getStringCellValue();
    }

    private String parseLecturer(Row row) {
        return row.getCell(Column.LECTURER.ordinal()).getStringCellValue();
    }

    private enum Column {
        MEETING, DATE, TIME, GROUP, SUBJECT, CLASSROOM, LECTURER
    }

}
