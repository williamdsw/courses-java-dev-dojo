package com.williamdsw.springbootessentials.handler;

import com.williamdsw.springbootessentials.error.ResourceNotFoundDetails;
import com.williamdsw.springbootessentials.error.ResourceNotFoundException;
import com.williamdsw.springbootessentials.error.ValidationErrorDetails;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author William
 */

@ControllerAdvice
public class RestExceptionHandler
{
    //------------------------------------------------------------------------//
    // EXCEPTION HANDLERS
    
    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException (ResourceNotFoundException exception)
    {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ResourceNotFoundDetails details = new ResourceNotFoundDetails ("Resource not found", notFound.value (), exception.getMessage (), new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, notFound);
    }
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception)
    {
        List<FieldError> fieldErrors = exception.getBindingResult ().getFieldErrors ();
        String fields = fieldErrors.stream ().map (FieldError::getField).collect (Collectors.joining (","));
        String fieldMessages = fieldErrors.stream ().map (FieldError::getDefaultMessage).collect (Collectors.joining (","));
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ValidationErrorDetails details = new ValidationErrorDetails (fields, fieldMessages, "Field Validation Error", badRequest.value (), "Field Validation Error", new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, badRequest);
    }
}