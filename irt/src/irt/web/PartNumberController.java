package irt.web;

import irt.objects.components.componentGroup.dao.ComponentGroupDAO;
import irt.objects.components.componentType.dao.ComponentTypeDAO;
import irt.web.form.PartNumberForm;

import javax.validation.Valid;

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

	private PartNumberForm partNumberForm;

	private ComponentGroupDAO componentGroupDAO;
	private ComponentTypeDAO componentTypeDAO;

	@RequestMapping(value="/part-numbers", method=RequestMethod.GET)
	public String partNumber(ModelMap modelMap) {

		modelMap.addAttribute("PNF", partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroupDAO.getComponentGroupList());
		modelMap.addAttribute("TYPES", componentTypeDAO.getComponentTypeList(partNumberForm.getComponentGroup().getId()));

		return "part-numbers";
	}

	@RequestMapping(value="/part-numbers", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
	public String getPartNumber(@Valid PartNumberForm partNumberForm, ModelMap modelMap, BindingResult result) {

		modelMap.addAttribute("PNF", this.partNumberForm = partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroupDAO.getComponentGroupList());
		modelMap.addAttribute("TYPES", componentTypeDAO.getComponentTypeList(partNumberForm.getComponentGroup().getId()));

		return "part-numbers";
	}

	public PartNumberForm getPartNumberForm() {
		return partNumberForm;
	}

	@Autowired
	public void setPartNumberForm(PartNumberForm partNumberForm) {
		this.partNumberForm = partNumberForm;
	}

	public ComponentGroupDAO getComponentGroupDAO() {
		return componentGroupDAO;
	}

	@Autowired
	public void setComponentGroupDAO(ComponentGroupDAO componentGroupDAO) {
		this.componentGroupDAO = componentGroupDAO;
	}

	public ComponentTypeDAO getComponentTypeDAO() {
		return componentTypeDAO;
	}

	@Autowired
	public void setComponentTypeDAO(ComponentTypeDAO componentTypeDAO) {
		this.componentTypeDAO = componentTypeDAO;
	}
}