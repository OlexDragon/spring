package irt.tools.table;


public class Field {

	private Object value;
	private String className;//use with CSS(HTML)
	private boolean visible = true;	
	private String href;

	public Field(Object value, String className, String href) {
		setValue(value);
		setClassName(className);
		setHref(href);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if(value!=null)
			this.value = value;
		else
			this.value = "";
	}

	protected String getBody(){
		return (className.isEmpty() ? "" : " class=\""+className+"\"")+">" + (href==null ? value : "<a href=\""+href+"\">"+value+"</a>");
	}

	@Override
	public String toString() {
		return visible ? "<td" +getBody() + "</td>" : "";
	}

	public void setClassName(String className) {
		if(className!=null)
			this.className = className;
		else
			this.className = "";
	}

	public String getClassName() {
		return className;
	}

	@Override
	public boolean equals(Object obj) {
		return obj!=null ? obj.hashCode()==hashCode() : false;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
