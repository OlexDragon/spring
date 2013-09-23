package irt.web;

import irt.objects.components.dao.ManufactureDAO;
import irt.web.form.ManufactureForm;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManufactureController {

	private ManufactureForm manufactureForm;

	private ManufactureDAO manufactureDAO;

	@RequestMapping(value="/manufacture-links", method=RequestMethod.GET)
	public String getManufactures(ModelMap modelMap) throws SQLException {

		manufactureForm.setTable(manufactureDAO.getTable(true, null));
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

	public ManufactureDAO getManufactureDAO() {
		return manufactureDAO;
	}

	@Autowired
	public void setManufactureDAO(ManufactureDAO manufactureDAO) {
		this.manufactureDAO = manufactureDAO;
	}
}