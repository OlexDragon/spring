package irt.web;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.web.form.PartNumberForm;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PartNumberController {

	private PartNumberForm partNumberForm;
	private List<ComponentGroup> componentGroups;

	@RequestMapping(value="/part-numbers", method=RequestMethod.GET)
	public String partNumber(ModelMap modelMap) {
		modelMap.addAttribute("PNF", partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroups);
		return "part-numbers";
	}

	@RequestMapping(value="/part-numbers", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
	public String getPartNumber(@Valid @ModelAttribute PartNumberForm o, ModelMap modelMap, BindingResult result) {

		System.out.println("getPartNumber : "+o.getClass().getSimpleName());
		System.out.println(o);
			
		modelMap.addAttribute("PNF", partNumberForm);
		modelMap.addAttribute("GROUPS", componentGroups);
		return "part-numbers";
	}

	public PartNumberForm getPartNumberForm() {
		return partNumberForm;
	}

	@Autowired
	public void setPartNumberForm(PartNumberForm partNumberForm) {
		this.partNumberForm = partNumberForm;
	}

	public List<ComponentGroup> getComponentGroups() {
		return componentGroups;
	}

	public void setComponentGroups(List<ComponentGroup> componentGroups) {
		this.componentGroups = componentGroups;
	}
}