package exceptions;

public class ManagerNotEnrolledException extends Exception {

    public ManagerNotEnrolledException() {
    }

    public ManagerNotEnrolledException(String msg) {
        super(msg);
    }
}