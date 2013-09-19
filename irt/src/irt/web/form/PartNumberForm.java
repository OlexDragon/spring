package irt.web.form;

import org.springframework.beans.factory.annotation.Autowired;

import irt.objects.components.componentGroup.ComponentGroup;

public class PartNumberForm {

	private ComponentGroup componentGroup;

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

	@Override
	public String toString() {
		return "PartNumberForm [componentGroup=" + componentGroup + "]";
	}
}
