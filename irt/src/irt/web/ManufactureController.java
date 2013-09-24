package irt.web;

import irt.objects.components.dao.ManufactureDAO;
import irt.web.form.ManufactureForm;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManufactureController {

	private ManufactureForm manufactureForm;

	private ManufactureDAO manufactureDAO;

	private boolean add;

	@RequestMapping(value="/manufacture-links", method=RequestMethod.GET)
	public String getManufactures(ModelMap modelMap) throws SQLException {

		manufactureForm.setTable(manufactureDAO.getTable(true, "/irt/manufacture-links"));
		modelMap.addAttribute("MFRF", manufactureForm);
		return "manufacture-links";
	}

	@RequestMapping(value="/manufacture-links/{orderBy}", method=RequestMethod.GET)
	public String getManufactures(@PathVariable("orderBy") String orderBy, ModelMap modelMap) throws SQLException {

		manufactureDAO.setOrderBy(orderBy);
		manufactureForm.setTable(manufactureDAO.getTable(true, "/irt/manufacture-links"));
		modelMap.addAttribute("MFRF", manufactureForm);
		return "manufacture-links";
	}

	@RequestMapping(value="/manufacture-links/**", method=RequestMethod.POST)
	public String getManufacturesAction(@RequestParam String mfr_btn, ModelMap modelMap) throws SQLException {

		switch(mfr_btn){
		case "Add":
			add = true;
			break;
		case "Cansel":
			add = false;
		};
		manufactureForm.setTable(manufactureDAO.getTable(true, "/irt/manufacture-links"));
		modelMap.addAttribute("MFRF", manufactureForm);
		modelMap.addAttribute("ADD", add);
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