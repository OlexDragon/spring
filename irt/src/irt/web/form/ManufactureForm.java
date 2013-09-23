package irt.web.form;

import org.springframework.beans.factory.annotation.Autowired;

import irt.objects.components.Manufacture;

public class ManufactureForm {

	private Manufacture manufacture;
	private static String table = "Table";

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		ManufactureForm.table = table;
	}

	public Manufacture getManufacture() {
		return manufacture;
	}

	@Autowired
	public void setManufacture(Manufacture manufacture) {
		this.manufacture = manufacture;
	}
}