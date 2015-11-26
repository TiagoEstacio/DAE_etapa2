package exceptions;

public class PasswordValidationException extends Exception {

    public PasswordValidationException() {
    }

    public PasswordValidationException(String msg) {
        super(msg);
    }
}
