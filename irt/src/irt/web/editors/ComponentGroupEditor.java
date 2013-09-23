package irt.web.editors;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.componentGroup.dao.ComponentGroupDAO;
import irt.web.PartNumberController;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ComponentGroupEditor extends PropertyEditorSupport {

	private ComponentGroupDAO componentGroupDAO;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		ComponentGroup componentGroup = new ComponentGroup();
		componentGroup.setId(text.charAt(0));

		List<ComponentGroup> componentGroups = PartNumberController.getComponentGroups();

		int index = componentGroups.indexOf(componentGroup);
		if(index>=0)
			componentGroup.setDescription(componentGroups.get(index).getDescription());

		setValue(componentGroup);
	}

	public ComponentGroupDAO getComponentGroupDAO() {
		return componentGroupDAO;
	}

	@Autowired
	public void setComponentGroupDAO(ComponentGroupDAO componentGroupDAO) {
		this.componentGroupDAO = componentGroupDAO;
	}
}
