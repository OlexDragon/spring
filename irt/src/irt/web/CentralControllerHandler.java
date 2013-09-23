package irt.web;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentType.ComponentType;
import irt.web.editors.ComponentGroupEditor;
import irt.web.editors.ComponentTypeEditor;
import irt.web.exceptions.PartNumberNotFoundException;
import irt.web.validators.IRTValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	Logger logger = LogManager.getLogger(CentralControllerHandler.class.getName());

	private ComponentGroupEditor componentGroupEditor;
	private ComponentTypeEditor componentTypeEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("initBinder");
		binder.setValidator(new IRTValidator());
		binder.registerCustomEditor(ComponentGroup.class, componentGroupEditor);
		binder.registerCustomEditor(ComponentType.class, componentTypeEditor);
	}

	@ExceptionHandler({ PartNumberNotFoundException.class })
	public ResponseEntity<String> handlePersonNotFound( PartNumberNotFoundException pe) {
		logger.info("handlePersonNotFound");
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<String> handleValidationException( MethodArgumentNotValidException pe) {
		logger.info("handleValidationException");
		return new ResponseEntity<String>(pe.getMessage(), HttpStatus.BAD_REQUEST);
	}

	public ComponentGroupEditor getComponentGroupEditor() {
		return componentGroupEditor;
	}

	@Autowired
	public void setComponentGroupEditor(ComponentGroupEditor componentGroupEditor) {
		logger.info("setComponentGroupEditor:"+componentGroupEditor);
		this.componentGroupEditor = componentGroupEditor;
	}

	public ComponentTypeEditor getComponentTypeEditor() {
		return componentTypeEditor;
	}

	@Autowired
	public void setComponentTypeEditor(ComponentTypeEditor componentTypeEditor) {
		logger.info("setComponentGroupEditor:"+componentTypeEditor);
		this.componentTypeEditor = componentTypeEditor;
	}

}