package irt.web;

import java.util.List;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentGroup.dao.ComponentGroupDAO;
import irt.objects.components.componentType.ComponentType;
import irt.objects.components.componentType.dao.ComponentTypeDAO;
import irt.web.form.PartNumberForm;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PartNumberController {

	Logger logger = LogManager.getLogger(PartNumberController.class.getName());

	private PartNumberForm partNumberForm;

	private static ComponentGroupDAO componentGroupDAO;
	private static ComponentTypeDAO componentTypeDAO;

	private static List<ComponentGroup> componentGroups;
	private static List<ComponentType> componentTypes;

	@RequestMapping(value="/part-numbers", method=RequestMethod.GET)
	public String partNumber(ModelMap modelMap) {

		componentGroups = componentGroupDAO.getComponentGroups();
		componentTypes = componentTypeDAO.getComponentTypes(partNumberForm.getComponentGroup().getId());

		modelMap.addAttribute("PNF", partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroups);
		modelMap.addAttribute("TYPES", componentTypes);

		return "part-numbers";
	}

	@RequestMapping(value="/part-numbers", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
	public String getPartNumber(@Valid PartNumberForm partNumberForm, ModelMap modelMap, BindingResult result) {

		logger.info("getPartNumber:"+partNumberForm);
		modelMap.addAttribute("PNF", this.partNumberForm = partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroups);
		modelMap.addAttribute("TYPES", componentTypes);

		return "part-numbers";
	}

	public PartNumberForm getPartNumberForm() {
		return partNumberForm;
	}

	@Autowired
	public void setPartNumberForm(PartNumberForm partNumberForm) {
		logger.info("setPartNumberForm:"+partNumberForm);
		this.partNumberForm = partNumberForm;
	}

	public ComponentGroupDAO getComponentGroupDAO() {
		return componentGroupDAO;
	}

	@Autowired
	public void setComponentGroupDAO(ComponentGroupDAO componentGroupDAO) {
		logger.info("setComponentGroupDAO:"+componentGroupDAO);
		PartNumberController.componentGroupDAO = componentGroupDAO;
	}

	public ComponentTypeDAO getComponentTypeDAO() {
		return componentTypeDAO;
	}

	@Autowired
	public void setComponentTypeDAO(ComponentTypeDAO componentTypeDAO) {
		logger.info("setComponentTypeDAO:"+componentTypeDAO);
		PartNumberController.componentTypeDAO = componentTypeDAO;
	}

	public static List<ComponentGroup> getComponentGroups() {
		return componentGroups!=null ? componentGroups : componentGroupDAO.getComponentGroups();
	}

	public static List<ComponentType> getComponentTypes() {
		return componentTypes!=null ? componentTypes : componentTypeDAO.getComponentTypes();
	}
}