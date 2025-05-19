package library.exception;

public class PatronAlreadyExistException extends RuntimeException {
    public PatronAlreadyExistException(String message) {
        super(message);
    }
}
