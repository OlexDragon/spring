package irt.objects.components.componentType;

public class ComponentType {

	private int classId;
	private char groupId;
	private String type;
	private String description;

	public char getGroupId() {
		return groupId;
	}

	public void setGroupId(char groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public int hashCode() {
		return classId>0 ? classId : super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj!=null ? obj.hashCode()==hashCode() : false;
	}
}
