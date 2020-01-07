package com.williamdsw.springbootessentials.handler;

import com.williamdsw.springbootessentials.error.ErrorDetails;
import com.williamdsw.springbootessentials.error.ResourceNotFoundDetails;
import com.williamdsw.springbootessentials.error.ResourceNotFoundException;
import com.williamdsw.springbootessentials.error.ValidationErrorDetails;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author William
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
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
    
    @ExceptionHandler (PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException (PropertyReferenceException exception)
    {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorDetails details = new ErrorDetails ("Property not found!", badRequest.value (), exception.getMessage (), new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, badRequest);
    }
    
    //------------------------------------------------------------------------//
    // OVERRIDED EXCEPTION HANDLERS

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        List<FieldError> fieldErrors = exception.getBindingResult ().getFieldErrors ();
        String fields = fieldErrors.stream ().map (FieldError::getField).collect (Collectors.joining (","));
        String fieldMessages = fieldErrors.stream ().map (FieldError::getDefaultMessage).collect (Collectors.joining (","));
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ValidationErrorDetails details = new ValidationErrorDetails (fields, fieldMessages, "Field Validation Error", badRequest.value (), "Field Validation Error", new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, badRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal (Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetails details = new ErrorDetails ("Internal Exception", status.value (), exception.getMessage (), new Date ().getTime (), exception.getClass ().getName ());
        return new ResponseEntity<>(details, headers, status);
    }
}