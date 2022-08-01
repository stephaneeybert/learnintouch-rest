package com.thalasoft.learnintouch.rest.exception;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

public class AbstractExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(AbstractExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	protected String localizeErrorMessage(String errorCode, Object args[]) {
		Locale locale = LocaleContextHolder.getLocale();
//		logger.debug("==========>> locale: " + locale);
		String errorMessage = messageSource.getMessage(errorCode, args, locale);
		return errorMessage;
	}

	protected String localizeErrorMessage(String errorCode) {
		return localizeErrorMessage(errorCode, null);
	}
	
	protected String extractAdminIdFromUrl(String url) {
		String adminId = null;
		try {
			URI uri = new URI(url);
			String path = uri.getPath();
			adminId = path.substring(path.lastIndexOf('/') + 1);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
        return adminId;
	}

	protected List<ErrorFormField> populateFieldErrors(List<FieldError> fieldErrorList) {
      List<ErrorFormField> errorFormFields = new ArrayList<ErrorFormField>();
      StringBuilder errorMessage = new StringBuilder("");
      for (FieldError fieldError : fieldErrorList) {
          errorMessage.append(fieldError.getCode()).append(".");
          errorMessage.append(fieldError.getObjectName()).append(".");
          errorMessage.append(fieldError.getField());
          errorFormFields.add(new ErrorFormField(fieldError.getField(), localizeErrorMessage(errorMessage.toString())));
          errorMessage.delete(0, errorMessage.capacity());
      }
      return errorFormFields;
  }

}
