package it.epicode.exeptions;

public class ExistingIsbnException extends Exception {
    public ExistingIsbnException(){}
    public ExistingIsbnException(String message) {
        super(message);
    }
}
