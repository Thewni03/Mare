package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice

public class SalesNotFoundAdvice {
    @ExceptionHandler(SalesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String SalesNotFoundHandler(vehicalNotFoundException ex) {
        return ex.getMessage();
    }
}





