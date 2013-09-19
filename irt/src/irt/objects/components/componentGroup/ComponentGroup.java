package irt.objects.components.componentGroup;

public class ComponentGroup {

	private char id;
	private String description;

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		return obj!=null ? obj.hashCode()==hashCode() : false;
	}

	@Override
	public int hashCode() {
		return id>0 ? new Integer(id) : super.hashCode();
	}

	@Override
	public String toString() {
		return "ComponentGroup [id=" + id + ", description=" + description + "]";
	}
}
