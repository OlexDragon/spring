package irt.tools.table;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private String className;	//use with CSS(HTML)
	private List<Field> fields = new ArrayList<>();

	@Override
	public String toString() {
		String returnStr = "<tr"+(className!=null ? " class=\""+className+"\" " : "")+">";

		for (Field f:fields) 
			returnStr += f;

		return returnStr + "</tr>";
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void add(Field field) {
		fields.add(field);
	}
}
