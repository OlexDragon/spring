package irt.objects.components.eventHandler;

import irt.objects.components.componentGroup.ComponentGroup;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class ComponentGroupEvent extends ApplicationEvent {

	public ComponentGroupEvent(ComponentGroup oldSelectedGroup, ComponentGroup newSelectedGroup) {
		super(new ComponentGroupEventObject(oldSelectedGroup, newSelectedGroup));
	}

}
