package library.exception;

public class BranchAlreadyExistException extends RuntimeException {
    public BranchAlreadyExistException(String message) {
        super(message);
    }
}
