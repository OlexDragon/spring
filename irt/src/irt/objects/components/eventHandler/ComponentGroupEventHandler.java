package irt.objects.components.eventHandler;

import irt.objects.components.componentType.ComponentTypeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class ComponentGroupEventHandler implements ApplicationListener<ComponentGroupEvent> {

	private ComponentTypeController componentTypeController;

	public ComponentTypeController getComponentTypeController() {
		return componentTypeController;
	}

	@Autowired
	public void setComponentTypeController(ComponentTypeController componentTypeController) {
		this.componentTypeController = componentTypeController;
	}

	@Override
	public void onApplicationEvent(ComponentGroupEvent event) {
		componentTypeController.setSelectedType(((ComponentGroupEventObject)event.getSource()).getNewSelectedGroup());
	}
}
