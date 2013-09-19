package irt.objects.components.eventHandler;

import irt.objects.components.componentGroup.ComponentGroup;

public class ComponentGroupEventObject {

	private ComponentGroup oldSelectedGroup;
	private ComponentGroup newSelectedGroup;

	public ComponentGroupEventObject(ComponentGroup oldSelectedGroup, ComponentGroup newSelectedGroup) {
		this.oldSelectedGroup = oldSelectedGroup;
		this.newSelectedGroup = newSelectedGroup;
	}

	public ComponentGroup getOldSelectedGroup() {
		return oldSelectedGroup;
	}

	public ComponentGroup getNewSelectedGroup() {
		return newSelectedGroup;
	}

	@Override
	public String toString() {
		return "ComponentGroupEventObject [oldSelectedGroup="
				+ oldSelectedGroup + ", newSelectedGroup=" + newSelectedGroup
				+ "]";
	}
}
