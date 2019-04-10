package ioiobagiety.util;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classes.Subject;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.model.user.UserType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XLSXParser {

    public ArrayList<Lesson> parseXLSX (MultipartFile file) throws MultipartException {
        try {
            XSSFWorkbook offices = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = offices.getSheetAt(0);

            XLSXParser parser = new XLSXParser();

            return parser.parseSheet(worksheet);
        } catch (Exception e) {
            throw new MultipartException("Constraints Violated");
        }
    }

    public ArrayList<Lesson> parseSheet (Sheet worksheet) throws ParseException {
        String schedule_name = worksheet.getSheetName();
        Map<String, Subject> subjects = getSubjects(worksheet);
        Map<String, Classroom> classrooms = getClassrooms(worksheet);
        Map<String, AppUser> lecturers = getLecturers(worksheet);

        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();

        ArrayList<Lesson> lessons = new ArrayList<>();

        int meetingNumber = 1;
        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getLastCellNum() % 3 == 0) {
                meetingNumber = parseMeetingNumber(row, meetingNumber);
                Date date = parseDate(row);
                Time startsAt = parseStartTime(row);
                Time endsAt = parseEndsTime(row);

                for (int i = 1; COLUMN.SUBJECT.value(i) < row.getLastCellNum(); i++) {
                    if (validCell(row, COLUMN.SUBJECT.value(i))) {
                        String subject = parseSubject(row, i);
                        String classroom = parseClassroom(row, i);
                        String lecturer = String.join(" ", parseLecturer(row, i));
                        Lesson lesson = new Lesson();
                        lesson.setScheduleName(schedule_name);
                        lesson.setMeetingNumber(meetingNumber);
                        lesson.setDate(date);
                        lesson.setStartsAt(startsAt);
                        lesson.setEndsAt(endsAt);
                        lesson.setSubject(subjects.get(subject));
                        lesson.setClassroom(classrooms.get(classroom));
                        lesson.setLecturer(lecturers.get(lecturer));
                        lessons.add(lesson);
                    }
                }
            }
        }

        return lessons;
    }

    private ArrayList<String> getHeaders (Sheet worksheet) {
        Row headerRow = worksheet.getRow(0);
        ArrayList<String> headers = new ArrayList<>();
        Iterator<Cell> cells = headerRow.cellIterator();
        while (cells.hasNext()) {
            headers.add(cells.next().getStringCellValue());
        }
        return headers;
    }

    private Map<String, Subject> getSubjects (Sheet worksheet) {
        HashMap<String, Subject> subjects = new HashMap<>();
        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();
            for (int i = 1; COLUMN.SUBJECT.value(i) < row.getLastCellNum(); i++) {
                if (validCell(row, COLUMN.SUBJECT.value(i))) {
                    String name = parseSubject(row, i);
                    if (!subjects.containsKey(name)) {
                        Subject subject = new Subject();
                        subject.setName(name);
                        subjects.put(name, subject);
                    }
                }
            }
        }

        return subjects;
    }

    private Map<String, Classroom> getClassrooms (Sheet worksheet) {
        HashMap<String, Classroom> classrooms = new HashMap<>();
        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();
            for (int i = 1; COLUMN.CLASSROOM.value(i) < row.getLastCellNum(); i++) {
                if (validCell(row, COLUMN.CLASSROOM.value(i))) {
                    String number = parseClassroom(row, i);
                    if (!classrooms.containsKey(number)) {
                        Classroom classroom = new Classroom();
                        classroom.setNumber(number);
                        classrooms.put(number, classroom);
                    }
                }
            }
        }

        return classrooms;
    }

    private Map<String, AppUser> getLecturers (Sheet worksheet) {
        HashMap<String, AppUser> lecturers = new HashMap<>();
        Iterator<Row> rows = worksheet.rowIterator();
        rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();
            for (int i = 1; COLUMN.LECTURER.value(i) < row.getLastCellNum(); i++) {
                if (validCell(row, COLUMN.LECTURER.value(i))) {
                    String[] name = parseLecturer(row, i);
                    String fullName = String.join(" ", name);
                    if (!lecturers.containsKey(fullName)) {
                        AppUser lecturer = new AppUser();
                        lecturer.setUserType(UserType.lecturer);
                        lecturer.setName(name[0]);
                        lecturer.setSurname(name[1]);
                        lecturers.put(fullName, lecturer);
                    }
                }
            }
        }

        return lecturers;
    }

    private int parseMeetingNumber (Row row, int lastNumber) {
        Cell cell = row.getCell(COLUMN.MEETING.value());
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return lastNumber;
    }

    private Date parseDate (Row row) throws ParseException {
        String date = row.getCell(COLUMN.DATE.value()).getStringCellValue();
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    private Time parseStartTime (Row row) throws ParseException {
        String startAt = row.getCell(COLUMN.TIME.value()).getStringCellValue().split("-")[0];
        Date startDate = new SimpleDateFormat("HH:mm").parse(startAt);
        return new Time(startDate.getTime());
    }

    private Time parseEndsTime (Row row) throws ParseException {
        String endsAt = row.getCell(COLUMN.TIME.value()).getStringCellValue().split("-")[1];
        Date endDate = new SimpleDateFormat("HH:mm").parse(endsAt);
        return new Time(endDate.getTime());
    }

    private String parseSubject (Row row, int group) {
        return row.getCell(COLUMN.SUBJECT.value(group)).getStringCellValue();
    }

    private String parseClassroom (Row row, int group) {
        return row.getCell(COLUMN.CLASSROOM.value(group)).getStringCellValue();
    }

    private String[] parseLecturer (Row row, int group) {
        return row.getCell(COLUMN.LECTURER.value(group)).getStringCellValue().split(" ");
    }

    private boolean validCell (Row row, int i) {
        return row.getCell(i, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getCellTypeEnum() != CellType.BLANK;
    }

    private enum COLUMN {
        MEETING, DATE, TIME, SUBJECT, CLASSROOM, LECTURER;

        public int value () {
            return value(1);
        }

        public int value (int group) {
            switch (this) {
                case MEETING:
                    return 0;
                case DATE:
                    return 1;
                case TIME:
                    return 2;
                case SUBJECT:
                    return group * 3;
                case CLASSROOM:
                    return group * 3 + 1;
                case LECTURER:
                    return group * 3 + 2;
                default:
                    return 0;
            }
        }
    }
}
