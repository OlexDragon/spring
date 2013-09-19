package irt.objects.components.componentGroup;

import irt.objects.components.eventHandler.ComponentGroupEventPublisher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ComponentGroupController {

	private ComponentGroupEventPublisher componentGroupEventPublisher;
	private List<ComponentGroup> componentGroups;
	private ComponentGroup selectedGroup;

	@Autowired
	public void setComponentGroupEventPublisher(ComponentGroupEventPublisher componentGroupEventPublisher) {
		this.componentGroupEventPublisher = componentGroupEventPublisher;
	}

	public List<ComponentGroup> getComponentGroups() {
		return componentGroups;
	}

	public void setComponentGroups(List<ComponentGroup> componentGroups) {
		this.componentGroups = componentGroups;
	}

	public ComponentGroup getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(ComponentGroup selectedGroup) {
		if(selectedGroup==null && this.selectedGroup!=null || selectedGroup!=null && !selectedGroup.equals(this.selectedGroup)){
			componentGroupEventPublisher.publish(this.selectedGroup, selectedGroup);
			this.selectedGroup = selectedGroup;
		}
	}

	@Override
	public String toString() {
		return "ComponentGroupController [selectedGroup=" + selectedGroup + ", componentGroups=" + componentGroups + "]";
	}
}
