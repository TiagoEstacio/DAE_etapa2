package exceptions;

public class EventNotEnrolledException extends Exception {

    public EventNotEnrolledException() {
    }

    public EventNotEnrolledException(String msg) {
        super(msg);
    }
}
