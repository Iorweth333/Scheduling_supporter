package ioiobagiety.util;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.model.classes.Subject;
import ioiobagiety.model.classroom.Classroom;
import ioiobagiety.model.user.AppUser;
import ioiobagiety.model.user.UserType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvParser {

    public ArrayList<Lesson> parseCsv (MultipartFile file) throws MultipartException {
        String schedule_name = file.getOriginalFilename().replace(".csv", "");
        ArrayList<Lesson> lessons = new ArrayList<>();

        try (
                Reader reader = new InputStreamReader(file.getInputStream());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            records.remove(0);
            Map<String, Subject> subjects = getSubjects(records);
            Map<String, Classroom> classrooms = getClassrooms(records);
            Map<String, AppUser> lecturers = getLecturers(records);

            int meetingNumber = 1;
            for (CSVRecord record : records) {
                if (record.size() >= 6 && record.size() % 3 == 0) {
                    meetingNumber = parseMeetingNumber(record, meetingNumber);
                    Date date = parseDate(record);
                    Time startsAt = parseStartTime(record);
                    Time endsAt = parseEndsTime(record);

                    for (int i = 1; COLUMN.SUBJECT.value(i) < record.size(); i++) {
                        String subject = parseSubject(record, i);
                        String classroom = parseClassroom(record, i);
                        String lecturer = String.join(" ", parseLecturer(record, i));
                        if (!subject.equals("")) {
                            Lesson lesson = new Lesson();
                            lesson.setMeetingNumber(meetingNumber);
                            lesson.setScheduleName(schedule_name);
                            lesson.setDate(date);
                            lesson.setStartsAt(startsAt);
                            lesson.setEndsAt(endsAt);
                            lesson.setSubject(subjects.get(subject));
                            lesson.setLecturer(lecturers.get(lecturer));
                            lesson.setClassroom(classrooms.get(classroom));
                            lessons.add(lesson);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new MultipartException("Constraints Violated");
        }
        return lessons;
    }


    private Map<String, Subject> getSubjects (List<CSVRecord> records) {
        HashMap<String, Subject> subjects = new HashMap<>();
        for (CSVRecord record : records) {
            for (int i = 1; COLUMN.SUBJECT.value(i) < record.size(); i++) {
                String name = parseSubject(record, i);
                if (!name.equals("") && !subjects.containsKey(name)) {
                    Subject subject = new Subject();
                    subject.setName(name);
                    subjects.put(name, subject);
                }

            }
        }

        return subjects;
    }

    private Map<String, Classroom> getClassrooms (List<CSVRecord> records) {
        HashMap<String, Classroom> classrooms = new HashMap<>();
        for (CSVRecord record : records) {
            for (int i = 1; COLUMN.CLASSROOM.value(i) < record.size(); i++) {
                String number = parseClassroom(record, i);
                if (!number.equals("") && !classrooms.containsKey(number)) {
                    Classroom classroom = new Classroom();
                    classroom.setNumber(number);
                    classrooms.put(number, classroom);
                }

            }
        }

        return classrooms;
    }

    private Map<String, AppUser> getLecturers (List<CSVRecord> records) {
        HashMap<String, AppUser> lecturers = new HashMap<>();
        for (CSVRecord record : records) {
            for (int i = 1; COLUMN.LECTURER.value(i) < record.size(); i++) {
                String[] name = parseLecturer(record, i);
                if (name.length > 1) {
                    String fullName = String.join(" ", name);
                    if (!lecturers.containsKey(fullName)) {
                        AppUser lecturer = new AppUser();
                        lecturer.setName(name[0]);
                        lecturer.setSurname(name[1]);
                        lecturer.setUserType(UserType.lecturer);
                        lecturers.put(fullName, lecturer);
                    }
                }
            }
        }

        return lecturers;
    }

    private int parseMeetingNumber (CSVRecord csvRecord, int lastNumber) {
        String value = csvRecord.get(COLUMN.MEETING.value());
        if (!value.equals("")) {
            return Integer.parseInt(value);
        }
        return lastNumber;
    }

    private Date parseDate (CSVRecord csvRecord) throws ParseException {
        String date = csvRecord.get(COLUMN.DATE.value());
        return new SimpleDateFormat("dd.MM.yyyy").parse(date);
    }

    private Time parseStartTime (CSVRecord csvRecord) throws ParseException {
        String startAt = csvRecord.get(COLUMN.TIME.value()).split("-")[0];
        Date startDate = new SimpleDateFormat("HH:mm").parse(startAt);
        return new Time(startDate.getTime());
    }

    private Time parseEndsTime (CSVRecord csvRecord) throws ParseException {
        String endsAt = csvRecord.get(COLUMN.TIME.value()).split("-")[1];
        Date endDate = new SimpleDateFormat("HH:mm").parse(endsAt);
        return new Time(endDate.getTime());
    }

    private String parseSubject (CSVRecord csvRecord, int group) {
        return csvRecord.get(COLUMN.SUBJECT.value(group));
    }

    private String parseClassroom (CSVRecord csvRecord, int group) {
        return csvRecord.get(COLUMN.CLASSROOM.value(group));
    }

    private String[] parseLecturer (CSVRecord csvRecord, int group) {
        return csvRecord.get(COLUMN.LECTURER.value(group)).split(" ");
    }

}
