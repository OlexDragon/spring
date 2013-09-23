package irt.web.form;

import org.springframework.beans.factory.annotation.Autowired;

import irt.objects.components.Manufacture;
import irt.tools.table.Table;

public class ManufactureForm {

	private Manufacture manufacture;
	private static Table table;

	public Table getTable() {
		return table ;
	}

	public void setTable(Table table) {
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