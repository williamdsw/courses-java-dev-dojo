package com.williamdsw.springbootessentials.handler;

import com.williamdsw.springbootessentials.error.ResourceNotFoundDetails;
import com.williamdsw.springbootessentials.error.ResourceNotFoundException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author William
 */

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException (ResourceNotFoundException exception)
    {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ResourceNotFoundDetails details = new ResourceNotFoundDetails ("Resource not found", notFound.value (), exception.getMessage (), new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, notFound);
    }
}