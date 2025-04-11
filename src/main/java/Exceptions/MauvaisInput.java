package Exceptions;

public class MauvaisInput extends RuntimeException {
    public MauvaisInput(String message) {
        super(message);
    }
}
