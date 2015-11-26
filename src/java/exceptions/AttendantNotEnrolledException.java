package exceptions;

public class AttendantNotEnrolledException extends Exception {

    public AttendantNotEnrolledException() {
    }

    public AttendantNotEnrolledException(String msg) {
        super(msg);
    }
}
