package ioiobagiety.exception;

public class XLSParseException extends RuntimeException {

    public XLSParseException(String s) {
        super(s);
    }

    public XLSParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
