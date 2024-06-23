package com.example.iottrackingsystem.exception;

import com.example.iottrackingsystem.dto.ErrorMessageDTO;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    static final Logger logger = LogManager.getLogger(CustomizedExceptionHandler.class);


    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(FileNotFoundException ex, WebRequest request) throws JsonParseException {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(ex.getMessage());
        logger.error("File not found exception : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(TechnicalIssueException.class)
    public final ResponseEntity<ErrorMessageDTO> handleTechnicalIssueException(TechnicalIssueException ex, WebRequest request) throws JsonParseException {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(ex.getMessage());
        logger.error("Internal Server error : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ErrorMessageDTO> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) throws JsonParseException {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(ex.getMessage());
        logger.error("Product Id not found : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<ErrorMessageDTO> handleInvalidInput(InvalidInputException ex, WebRequest request) throws JsonParseException {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(ex.getMessage());
        logger.error("Invalid input parameter : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public final ResponseEntity<ErrorMessageDTO> handleDeviceNotFoundException(DeviceNotFoundException ex, WebRequest request) throws JsonParseException {
        ErrorMessageDTO errorDetails = new ErrorMessageDTO(ex.getMessage());
        logger.error("Device Not Found Exception : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}
