package irt.web;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentType.ComponentType;
import irt.web.editors.ComponentGroupEditor;
import irt.web.editors.ComponentTypeEditor;
import irt.web.exceptions.PartNumberNotFoundException;
import irt.web.validators.PartNumberFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CentralControllerHandler {

	private ComponentGroupEditor componentGroupEditor;
	private ComponentTypeEditor componentTypeEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new PartNumberFormValidator());
		binder.registerCustomEditor(ComponentGroup.class, componentGroupEditor);
		binder.registerCustomEditor(ComponentType.class, componentTypeEditor);
	}

	@ExceptionHandler({ PartNumberNotFoundException.class })
	public ResponseEntity<String> handlePersonNotFound( PartNumberNotFoundException pe) {
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<String> handleValidationException( MethodArgumentNotValidException pe) {
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.BAD_REQUEST);
	}

	public ComponentGroupEditor getComponentGroupEditor() {
		return componentGroupEditor;
	}

	@Autowired
	public void setComponentGroupEditor(ComponentGroupEditor componentGroupEditor) {
		this.componentGroupEditor = componentGroupEditor;
	}

	public ComponentTypeEditor getComponentTypeEditor() {
		return componentTypeEditor;
	}

	@Autowired
	public void setComponentTypeEditor(ComponentTypeEditor componentTypeEditor) {
		this.componentTypeEditor = componentTypeEditor;
	}

}