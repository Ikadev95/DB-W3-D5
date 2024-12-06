package it.epicode.exeptions;

public class LibroNonTrovatoException extends Exception {
    LibroNonTrovatoException(){}
    public LibroNonTrovatoException(String message) {
        super(message);
    }
}
