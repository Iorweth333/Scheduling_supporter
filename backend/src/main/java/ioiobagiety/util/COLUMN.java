package ioiobagiety.util;

public enum COLUMN {
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
