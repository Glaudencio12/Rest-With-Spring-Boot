package br.com.Glaudencio12.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequireObjectIsNullException extends RuntimeException {
    public RequireObjectIsNullException() {
        super("It is not allowed to persist a null object!");
    }

    public RequireObjectIsNullException(String message) {
        super(message);
    }
}
