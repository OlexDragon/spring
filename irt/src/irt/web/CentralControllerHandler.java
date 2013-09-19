package irt.web;

import irt.exceptions.PartNumberNotFoundException;
import irt.validators.PartNumberFormValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CentralControllerHandler {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		System.out.println("Yes - initBinder");
		binder.setValidator(new PartNumberFormValidator());
	}

	@ExceptionHandler({ PartNumberNotFoundException.class })
	public ResponseEntity<String> handlePersonNotFound( PartNumberNotFoundException pe) {
		System.out.println("Yes");
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<String> handleValidationException( MethodArgumentNotValidException pe) {
		System.out.println("Yes - 2");
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.BAD_REQUEST);
	}

}