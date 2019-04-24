package ioiobagiety.exception;

public class XLSXParseException extends RuntimeException {

    public XLSXParseException (String s) {
        super(s);
    }

    public XLSXParseException (String s, Throwable throwable) {
        super(s, throwable);
    }
}
