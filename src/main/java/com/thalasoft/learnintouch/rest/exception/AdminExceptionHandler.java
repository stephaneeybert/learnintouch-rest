package com.thalasoft.learnintouch.rest.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thalasoft.learnintouch.data.exception.AdminAlreadyExistsException;

@ControllerAdvice
public class AdminExceptionHandler extends AbstractExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(AdminExceptionHandler.class);

//	@Autowired
//    private AdminResourceValidator adminResourceValidator;

//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setValidator(adminResourceValidator);
//	}
	
	@ExceptionHandler(AdminAlreadyExistsException.class)
	@ResponseBody
	public ResponseEntity<ErrorInfo> entityAlreadyExistException(HttpServletRequest request, AdminAlreadyExistsException e) {
    	logger.debug("Catching a EntityAlreadyExistsException");
		String url = request.getRequestURL().toString();
        String errorMessage = localizeErrorMessage("error.admin.already.exists", new Object[] { e.getEmail() });
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(url, errorMessage), HttpStatus.CONFLICT);
	}

}
