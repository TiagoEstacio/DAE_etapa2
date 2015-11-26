package exceptions;

public class EventDoesNotExistsException extends Exception {

    public EventDoesNotExistsException() {
    }

    public EventDoesNotExistsException(String msg) {
        super(msg);
    }
}
