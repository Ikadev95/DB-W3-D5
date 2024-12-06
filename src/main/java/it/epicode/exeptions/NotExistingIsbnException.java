package it.epicode.exeptions;

public class NotExistingIsbnException extends Exception {
    NotExistingIsbnException(){}
    public NotExistingIsbnException(String message) {
        super(message);
    }
}
