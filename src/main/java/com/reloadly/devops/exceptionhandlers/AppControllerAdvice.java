package com.reloadly.devops.exceptionhandlers;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.OptimisticLockException;
import javax.validation.UnexpectedTypeException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static com.reloadly.devops.constants.ResponseMessages.*;

import com.reloadly.devops.exceptions.*;
import com.reloadly.devops.response.dtos.ResponseDTO;

@RestControllerAdvice
public class AppControllerAdvice {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDTO<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    return new ResponseDTO<>(INVALIDFIELDS.getCode(), INVALIDFIELDS.getMessage(), errors);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseDTO<Map<String, String>> handleHttpExceptions(HttpClientErrorException ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("error2", ex.getMessage());
	    
	    return new ResponseDTO<>(INVALIDFIELDS.getCode(), INVALIDFIELDS.getMessage(), errors);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidUserException.class)
	public Map<String, Object> handleInvalidUserExceptions(InvalidUserException ex) {
	    Map<String, Object> error = new HashMap<>();
	    error.put("valid", false);
	    
	    return error;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AppException.class)
	public ResponseDTO<String> handleGenericAppException(AppException ex){
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidAccountNumberException.class)
	public ResponseDTO<String> handleInvalidAccountNumberException(InvalidAccountNumberException ex){
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseDTO<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), error);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseDTO<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(ex.getMethod());
		builder.append("method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
	
		return  new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), builder.toString());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseDTO<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
		return new ResponseDTO<>(DATAINTEGRITYERROR.getCode(), DATAINTEGRITYERROR.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseDTO<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseDTO<String> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(JpaObjectRetrievalFailureException.class)
	public ResponseDTO<String> handleJpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException ex) {
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseDTO<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(JpaSystemException.class)
	public ResponseDTO<String> handleJpaSystemException(JpaSystemException ex) {
		return new ResponseDTO<>(FAILURE.getCode(), FAILURE.getMessage(), ex.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UsernameMismatchException.class)
	public ResponseDTO<String> handleUsernameMismatchException(UsernameMismatchException ex){
		return new ResponseDTO<>(USERNAMEMISMATCH.getCode(), USERNAMEMISMATCH.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OptimisticLockException.class)
	public ResponseDTO<String> handleOptimisticLockException(OptimisticLockException ex) {
		return new ResponseDTO<>(OPTIMISTICLOCK.getCode(), OPTIMISTICLOCK.getMessage(), ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceAccessException.class)
	public ResponseDTO<String> handleResourceAccessException(ResourceAccessException ex) {
		return new ResponseDTO<>(EXTERNAL_SERVER_UNAVAILABLE.getCode(), EXTERNAL_SERVER_UNAVAILABLE.getMessage(), ex.getMessage());
	}
	
	
}