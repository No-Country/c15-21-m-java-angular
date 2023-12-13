package c1521mjavaangular.ecotienda.checkoutMP;

import java.security.SignatureException;

public class InvalidTokenException extends SignatureException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
