package jk.web.html.select;


public class HTMLOptionElement{

	private String value;
	private String innerHTML;
	private boolean disabled;
	private boolean selected;

	public String getValue() {
		return value;
	}
	public HTMLOptionElement setValue(String value) {
		this.value = value;
		return this;
	}
	public String getInnerHTML() {
		return innerHTML;
	}
	public HTMLOptionElement setInnerHTML(String innerHTML) {
		this.innerHTML = innerHTML;
		return this;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public HTMLOptionElement setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	@Override
	public String toString() {
		return "HTMLOption [value=" + value + ", innerHTML=" + innerHTML + ", disabled=" + disabled + ", selected=" + selected + "]";
	}
}
