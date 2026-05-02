package exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("User does not own the requested resource.");
    }
}