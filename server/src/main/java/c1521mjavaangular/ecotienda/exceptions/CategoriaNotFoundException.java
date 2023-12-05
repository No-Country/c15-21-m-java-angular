package c1521mjavaangular.ecotienda.exceptions;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }
}