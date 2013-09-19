package irt.objects.components.componentType;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentType.dao.ComponentTypeDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ComponentTypeController {

	private List<ComponentType> componentTypes;
	private ComponentTypeDAO componentTypeDAO;

	public ComponentTypeDAO getComponentTypeDAO() {
		return componentTypeDAO;
	}

	@Autowired
	public void setComponentTypeDAO(ComponentTypeDAO componentTypeDAO) {
		this.componentTypeDAO = componentTypeDAO;
	}

	public List<ComponentType> getComponentTypes() {
		return componentTypes;
	}

	public void setComponentTypes(List<ComponentType> componentTypes) {
		this.componentTypes = componentTypes;
	}

	public void setSelectedType(ComponentGroup selectedGroup) {
		if(selectedGroup==null)
			componentTypes = null;
		else
			componentTypes = componentTypeDAO.getListComponentGroup(selectedGroup.getId());
	}

	@Override
	public String toString() {
		return "ComponentTypeController [componentTypes=" + componentTypes + "]";
	}
}
