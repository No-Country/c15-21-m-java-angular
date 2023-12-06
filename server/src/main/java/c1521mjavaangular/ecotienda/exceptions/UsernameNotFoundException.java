package c1521mjavaangular.ecotienda.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException(String message) {
        super(message);
    }
}