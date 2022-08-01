package com.thalasoft.learnintouch.rest.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thalasoft.learnintouch.data.exception.CannotDeleteEntityException;
import com.thalasoft.learnintouch.data.exception.EntityAlreadyExistsException;
import com.thalasoft.learnintouch.data.exception.EntityNotFoundException;
import com.thalasoft.learnintouch.data.exception.NoEntitiesFoundException;

@ControllerAdvice
public class EntityExceptionHandler extends AbstractExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(EntityExceptionHandler.class);

	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> nullPointerException(HttpServletRequest request, NullPointerException e) {
    	logger.debug("Catching a NullPointerException");
    	String url = request.getRequestURL().toString();
        String errorMessage = localizeErrorMessage("error.npe");
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> invalidDataAccessApiUsageException(HttpServletRequest request, InvalidDataAccessApiUsageException e) {
    	logger.debug("Catching a InvalidDataAccessApiUsageException");
    	String url = request.getRequestURL().toString();
    	String errorMessage = e.getMessage();
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
    	logger.debug("Catching a HttpMessageNotReadableException");
    	String errorMessage = e.getMessage();
    	String url = request.getRequestURL().toString();
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> typeMismatchException(HttpServletRequest request, TypeMismatchException e) {
    	logger.debug("Catching a TypeMismatchException");
    	String url = request.getRequestURL().toString();
    	String errorMessage = localizeErrorMessage("error.entity.id.mismatch", new Object[] { e.getValue() });
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.BAD_REQUEST);
    }
   
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> numberFormatException(HttpServletRequest request, NumberFormatException e) {
    	logger.debug("Catching a NumberFormatException");
    	String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage(e.getMessage());
        return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.BAD_REQUEST);
    }
   
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> entityNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
    	logger.debug("Catching a EntityNotFoundException");
		String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityAlreadyExistsException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> entityAlreadyExistException(HttpServletRequest request, EntityAlreadyExistsException e) {
    	logger.debug("Catching a EntityAlreadyExistsException");
		String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NoEntitiesFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> noEntitiesFoundException(HttpServletRequest request, NoEntitiesFoundException e) {
    	logger.debug("Catching a NoEntitiesFoundException");
		String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CannotDeleteEntityException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> cannotDeleteEntityException(HttpServletRequest request, CannotDeleteEntityException e) {
    	logger.debug("Catching a CannotDeleteEntityException");
		String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ErrorFormInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
    	logger.debug("Catching a MethodArgumentNotValidException");
		String url = request.getRequestURL().toString();
		String errorMessage = localizeErrorMessage("error.entity.invalid.form.argument");
		ErrorFormInfo errorFormInfo = new ErrorFormInfo(url, errorMessage);
		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		errorFormInfo.getFieldErrors().addAll(populateFieldErrors(fieldErrors));
		return new ResponseEntity<ErrorFormInfo>(errorFormInfo, HttpStatus.BAD_REQUEST);
	}

}
