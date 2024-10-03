package UserAuthantication.UserCheckValidOrNot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor with message parameter.
     *
     * @param message Exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}