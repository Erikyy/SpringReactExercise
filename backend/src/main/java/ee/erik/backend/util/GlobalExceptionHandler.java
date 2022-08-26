package ee.erik.backend.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({
        PackageNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, WebRequest request) throws IOException {

        return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
