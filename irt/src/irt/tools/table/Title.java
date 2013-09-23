package irt.tools.table;

public class Title extends Field {

	public Title(String value, String className, String href) {
		super(value, className, href);
	}

	@Override
	public String toString() {
		return "<th" + getBody() + "</th>";
	}
}
