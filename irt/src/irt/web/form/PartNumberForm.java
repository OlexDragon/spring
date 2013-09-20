package irt.web.form;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentType.ComponentType;

import org.springframework.beans.factory.annotation.Autowired;

public class PartNumberForm {

	private ComponentGroup componentGroup;
	private ComponentType componentType;

	public ComponentGroup getComponentGroup() {
		return componentGroup;
	}

	@Autowired
	public void setComponentGroup(ComponentGroup componentGroup) {
		this.componentGroup = componentGroup;
	}

	public void setComponentGroup(String idStr) {
		char id = idStr.charAt(0);
		if(componentGroup.getId()!=id){
			componentGroup = new ComponentGroup();
			componentGroup.setId(id);
		}
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	@Autowired
	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	@Override
	public String toString() {
		return "PartNumberForm [componentGroup=" + componentGroup
				+ ", componentType=" + componentType + "]";
	}
}
