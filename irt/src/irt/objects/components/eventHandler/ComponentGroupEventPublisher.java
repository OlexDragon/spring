package irt.objects.components.eventHandler;

import irt.objects.components.componentGroup.ComponentGroup;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class ComponentGroupEventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher) {
	      this.publisher = publisher;
	}

	public void publish(ComponentGroup oldSelectedGroup, ComponentGroup newSelectedGroup) {
		ComponentGroupEvent cge = new ComponentGroupEvent(oldSelectedGroup, newSelectedGroup);
		publisher.publishEvent(cge);
	}
}
