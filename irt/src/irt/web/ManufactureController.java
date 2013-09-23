package irt.web;

import irt.web.form.ManufactureForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManufactureController {

	private ManufactureForm manufactureForm;

	@RequestMapping(value="/manufacture-links", method=RequestMethod.GET)
	public String getManufactures(ModelMap modelMap) {
		System.out.println("Yes - "+manufactureForm);
		modelMap.addAttribute("MFRF", manufactureForm);
		return "manufacture-links";
	}

	public ManufactureForm getManufactureForm() {
		return manufactureForm;
	}

	@Autowired
	public void setManufactureForm(ManufactureForm manufactureForm) {
		this.manufactureForm = manufactureForm;
	}
}